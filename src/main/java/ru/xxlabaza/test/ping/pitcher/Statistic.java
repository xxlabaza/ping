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

import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.val;

/**
 * Pitcher's communication statistic holder class.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 26.06.2017
 */
@FieldDefaults(
        level = PRIVATE,
        makeFinal = true
)
class Statistic {

    AtomicLong totalReceived = new AtomicLong(0L);

    LongAdder sendTime = new LongAdder();

    LongAdder receivedTime = new LongAdder();

    LongAdder sendCounter = new LongAdder();

    LongAdder receivedCounter = new LongAdder();

    LongAccumulator maximumTransmittionTime = new LongAccumulator(Long::max, 0L);

    void incrementSendMessagesCounter () {
        sendCounter.increment();
    }

    void incrementReceivedMessagesCounter () {
        receivedCounter.increment();
    }

    long addSendTime (long start) {
        val end = System.currentTimeMillis();
        sendTime.add(end - start);
        return end;
    }

    long addReceiveTime (long start) {
        val end = System.currentTimeMillis();
        receivedTime.add(end - start);
        return end;
    }

    void addTransmittionTime (long time) {
        maximumTransmittionTime.accumulate(time);
    }

    Snapshot getSnapshot () {
        val sendCounterCache = sendCounter.sumThenReset();
        val receivedCounterCache = receivedCounter.sumThenReset();

        val sendTimeCache = sendTime.sumThenReset();
        val receivedTimeCache = receivedTime.sumThenReset();
        val maximumTransmittionTimeCache = maximumTransmittionTime.get();

        val totalReceivedCache = totalReceived.addAndGet(receivedCounterCache);

        val averageTimeSending = sendTimeCache != 0 && sendCounterCache != 0
                                 ? sendTimeCache / sendCounterCache
                                 : 0;
        val averageTimeReceiving = receivedTimeCache != 0 && receivedCounterCache != 0
                                   ? receivedTimeCache / receivedCounterCache
                                   : 0;
        val averageTimeTransmittion = averageTimeSending + averageTimeReceiving;

        return Snapshot.builder()
                .totalSendAndReceived(totalReceivedCache)
                .lastTimeSendAndReceived(receivedCounterCache)
                .averageTimeSending(averageTimeSending)
                .averageTimeReceiving(averageTimeReceiving)
                .averageTimeTransmittion(averageTimeTransmittion)
                .maximumTimeTransmittion(maximumTransmittionTimeCache)
                .build();
    }

    /**
     * Pitcher's communication statistic snapshot holder class.
     *
     * @author Artem Labazin <xxlabaza@gmail.com>
     * @since 26.06.2017
     */
    @Value
    @Builder
    static class Snapshot {

        long totalSendAndReceived;

        long lastTimeSendAndReceived;

        long averageTimeTransmittion;

        long averageTimeSending;

        long averageTimeReceiving;

        long maximumTimeTransmittion;
    }
}
