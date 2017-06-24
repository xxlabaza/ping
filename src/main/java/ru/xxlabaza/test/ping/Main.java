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
package ru.xxlabaza.test.ping;

import com.beust.jcommander.ParameterException;
import lombok.extern.slf4j.Slf4j;
import ru.xxlabaza.test.ping.cli.CommandLineParser;

/**
 * Program's entry point.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 24.06.2017
 */
@Slf4j
public class Main {

    public static void main (String[] args) {
        try {
            CommandLineParser.parse(args);
        } catch (ParameterException ex) {
            System.exit(1);
        }

        log.info("main.info", 1, 2, 3);
        log.debug("main.debug");
    }
}
