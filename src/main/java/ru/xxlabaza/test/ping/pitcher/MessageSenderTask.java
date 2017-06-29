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
package ru.xxlabaza.test.ping.pitcher;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import javax.xml.bind.DatatypeConverter;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * A task for sending message via Socket.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 26.06.2017
 */
@Slf4j
@Value
@Builder
class MessageSenderTask implements Runnable {

    // 'messageSize + messageId' in bytes
    private final static int HEADER_SIZE = 10;

    private final static AtomicLong ID_COUNT = new AtomicLong(0L);

    InetAddress inetAddress;

    int port;

    short messageSize;

    Statistic statistic;

    @Override
    public void run () {
        val id = ID_COUNT.getAndIncrement();
        log.debug("pitcher.MessageSenderTask.before", id, inetAddress, port);

        try {
            sendMessage(id);
        } catch (Exception ex) {
            log.error("pitcher.MessageSenderTask.error", id, ex.getMessage());
            ex.printStackTrace();
        }
    }

    @SneakyThrows
    private void sendMessage (long id) {
        val message = createMessage(id);
        val startTime = System.currentTimeMillis();

        try (val socket = new Socket()) {
            socket.connect(new InetSocketAddress(inetAddress, port), (int) SECONDS.toMillis(10));
            socket.setSoTimeout((int) SECONDS.toMillis(20));

            val writer = new BufferedOutputStream(socket.getOutputStream());
            val reader = socket.getInputStream();

            writer.write(message);
            writer.flush();

            val sendTime = statistic.addSendTime(startTime);
            statistic.incrementSendMessagesCounter();
            log.debug("pitcher.MessageSenderTask.send", id);

            byte[] back = new byte[message.length];
            int count = 0;
            do {
                count += reader.read(back, count, back.length - count);
            } while (count != -1 && count != back.length);

            val receiveTime = statistic.addReceiveTime(sendTime);
            statistic.addTransmittionTime(receiveTime - startTime);
            statistic.incrementReceivedMessagesCounter();

            if (log.isDebugEnabled()) {
                log.debug("pitcher.MessageSenderTask.received", id, count, print(back));
            }
        }
    }

    private byte[] createMessage (long id) {
        val body = new byte[messageSize - HEADER_SIZE];
        ThreadLocalRandom.current().nextBytes(body);
        return ByteBuffer.allocate(messageSize)
                .putShort(messageSize)
                .putLong(id)
                .put(body)
                .array();
    }

    private String print (byte[] bytes) {
        val buffer = ByteBuffer.wrap(bytes);

        StringBuilder sb = new StringBuilder('\n')
                .append("size: ").append(buffer.getShort()).append('\n')
                .append("id: ").append(buffer.getLong()).append('\n');

        val hexString = DatatypeConverter.printHexBinary(buffer.array());
        return sb.append("body: ").append(hexString).toString();
    }
}
