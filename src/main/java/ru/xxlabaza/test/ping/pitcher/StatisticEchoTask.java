/*
 * Copyright 2017 xxlabaza.
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

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * A task for periodically statistic logging.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 26.06.2017
 */
@Slf4j
@Value
@Builder
class StatisticEchoTask implements Runnable {

    Statistic statistic;

    @Override
    public void run () {
        val snapshot = statistic.getSnapshot();
        log.info("pitcher.StatisticEchoTask.print",
                 snapshot.getTotalSendAndReceived(),
                 snapshot.getLastTimeSendAndReceived(),
                 snapshot.getMaximumTimeTransmittion(),
                 snapshot.getAverageTimeTransmittion(),
                 snapshot.getAverageTimeSending(),
                 snapshot.getAverageTimeReceiving());
    }
}
