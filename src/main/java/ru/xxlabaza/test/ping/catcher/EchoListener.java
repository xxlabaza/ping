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

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * The class, which reads incoming message via Socket and send it back.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 26.06.2017
 */
@Slf4j
@Value
class EchoListener implements Runnable {

    Socket socket;

    @Override
    @SneakyThrows
    public void run () {
        try (val reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             val writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true)) {

            val message = reader.readLine();
            log.debug("catcher.EchoListener.received", message);

            writer.println(message);
            log.debug("catcher.EchoListener.send");
        } finally {
            socket.close();
        }
    }
}
