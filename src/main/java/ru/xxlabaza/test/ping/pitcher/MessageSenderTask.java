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

import java.net.InetAddress;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Builder;
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
class SendMessageTask implements Runnable {

    private final static AtomicLong ID_COUNT = new AtomicLong(0L);

    private final static Random RANDOM = new Random(System.nanoTime());

    InetAddress inetAddress;

    int port;

    @Override
    public void run () {
        val id = ID_COUNT.getAndIncrement();
        log.info("pitcher.sendMessageTask.info", id, inetAddress, port);

        try {
            Thread.sleep(50 + RANDOM.nextInt(350));
        } catch (InterruptedException ex) {
            log.error("error: ", ex);
        }
    }
}
