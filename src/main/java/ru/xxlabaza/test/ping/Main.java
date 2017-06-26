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

import ru.xxlabaza.test.ping.cli.CommandLineParser;

/**
 * Program's entry point.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 24.06.2017
 */
public class Main {

    public static void main (String[] args) {
        CommandExecutor executor;
        try {
            executor = CommandLineParser.parse(args);
        } catch (Exception ex) {
            System.exit(1);
            return;
        }

        if (executor == null) {
            return;
        }

        executor.execute();
    }
}
