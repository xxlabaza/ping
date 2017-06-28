/*
 * Copyright 2017 Artem Labazin <xxlabaza@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.xxlabaza.test.ping.catcher;

import static java.net.InetAddress.getLocalHost;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.assertArrayEquals;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.val;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 28.06.2017
 */
public class CatcherCommandExecutorTest {

    private static final int PORT;

    private static final InetAddress ADDRESS;

    private static ExecutorService executorService;

    static {
        PORT = 8989;
        try {
            ADDRESS = getLocalHost();
        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        }
    }

    @BeforeClass
    @SneakyThrows
    public static void beforeClass () {
        val options = new CatcherCommandOptions();
        options.setPort(PORT);
        options.setBind(ADDRESS);

        val commandExecutor = CatcherCommandExecutor.builder()
                .options(options)
                .build();

        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> commandExecutor.execute());

        MILLISECONDS.sleep(200);
    }

    @AfterClass
    public static void afterClass () {
        executorService.shutdownNow();
    }

    @Test
    public void testEcho () {
        byte[] outcome = TestMessage.builder()
                .id(1L)
                .body("Hello world".getBytes())
                .build()
                .toArray();

        byte[] income = sendMessage(outcome);
        assertArrayEquals(outcome, income);
    }

    @SneakyThrows
    private byte[] sendMessage (byte[] message) {
        byte[] result = null;
        try (val socket = new Socket(ADDRESS, PORT)) {
            val writer = new BufferedOutputStream(socket.getOutputStream());
            val reader = new BufferedInputStream(socket.getInputStream());

            writer.write(message);
            writer.flush();

            result = new byte[message.length];
            while (reader.read(result) != -1) {
            }
        }
        return result;
    }

    @Value
    @Builder
    private static class TestMessage {

        long id;

        byte[] body;

        byte[] toArray () {
            short size = (short) (10 + body.length);
            return ByteBuffer.allocate(size)
                    .putShort(size)
                    .putLong(id)
                    .put(body)
                    .array();
        }
    }
}
