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

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.net.InetAddress;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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

    private final static AtomicLong ID_COUNT = new AtomicLong(0L);

    private final static Random RANDOM = new Random(System.nanoTime());

    InetAddress inetAddress;

    int port;

    Statistic statistic;

    @Override
    public void run () {
        val id = ID_COUNT.getAndIncrement();
        log.debug("pitcher.MessageSenderTask.before", id, inetAddress, port);

        statistic.incrementSendMessagesCounter();
        try {
            sendMessage(id);
            statistic.incrementReceivedMessagesCounter();
        } catch (Exception ex) {
            log.error("pitcher.MessageSenderTask.error", id, ex.getMessage());
        }
    }

    @SneakyThrows
    private void sendMessage (long id) {
        val startTime = System.currentTimeMillis();

        MILLISECONDS.sleep(RANDOM.nextInt(500));

        val sendTime = statistic.addSendTime(startTime);
        log.debug("pitcher.MessageSenderTask.send", id);

        MILLISECONDS.sleep(RANDOM.nextInt(500));

        val receiveTime = statistic.addReceiveTime(sendTime);
        log.debug("pitcher.MessageSenderTask.received", id);

        statistic.addTransmittionTime(receiveTime - startTime);
    }
}
