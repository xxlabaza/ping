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
import ru.xxlabaza.test.ping.CommandExecutor;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 25.06.2017
 */
@Slf4j
@Value
@Builder
public class PitcherCommandExecutor implements CommandExecutor {

    PitcherCommandOptions options;

    @Override
    public void execute () {
        log.info("pitcher.executor.options", options);
    }
}
