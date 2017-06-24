/**
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
package ru.xxlabaza.test.ping.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * Command line arguments parser utility class.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 24.06.2017
 */
public final class CommandLineParser {

    public static void parse (String[] args) {
        CommonOptions commonOptions = new CommonOptions();

        JCommander commander = JCommander.newBuilder()
                .programName("java -jar ping.jar")
                .addObject(commonOptions)
                .build();

        try {
            commander.parse(args);
        } catch (ParameterException ex) {
            System.err.format("\nParsing arguments failed.\n%s\n\n", ex.getMessage());
            commander.usage();
            System.exit(1);
        }

        if (commonOptions.isHelp()) {
            System.out.println("\nThe program’s aim is to test the IP network and to determine RTT (Round Trip Time)\n");
            commander.usage();
            System.exit(0);
        }
    }

    private CommandLineParser () {
    }
}
