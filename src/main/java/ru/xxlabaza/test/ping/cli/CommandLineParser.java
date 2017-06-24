/**
 * Copyright 2017 Artem Labazin <xxlabaza@gmail.com>.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.xxlabaza.test.ping.cli;

import static ch.qos.logback.classic.Level.DEBUG;
import static org.slf4j.Logger.ROOT_LOGGER_NAME;

import ch.qos.logback.classic.Logger;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.File;
import java.util.ResourceBundle;
import lombok.val;
import org.slf4j.LoggerFactory;
import ru.xxlabaza.test.ping.localization.UTF8Control;;

/**
 * Command line arguments parser utility class.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 24.06.2017
 */
public final class CommandLineParser {

    private static final ResourceBundle BUNDLE;

    private static final String PROGRAM_NAME;

    private static final String PROGRAM_DESCRIPTION;

    private static final String ARGUMENTS_ERROR_PREFIX;

    static {
        BUNDLE = ResourceBundle.getBundle("localization/options", new UTF8Control());

        PROGRAM_NAME = "java -jar " + getProgramName();

        PROGRAM_DESCRIPTION = BUNDLE.getString("program.description");
        ARGUMENTS_ERROR_PREFIX = BUNDLE.getString("program.argument.parse.error");
    }

    public static void parse (String[] args) {
        val commonOptions = new CommonOptions();

        val commander = JCommander.newBuilder()
                .programName(PROGRAM_NAME)
                .addObject(commonOptions)
                .resourceBundle(BUNDLE)
                .build();

        try {
            commander.parse(args);
        } catch (ParameterException ex) {
            System.err.format(ARGUMENTS_ERROR_PREFIX, ex.getMessage());
            commander.usage();
            System.exit(1);
        }

        if (commonOptions.isHelp()) {
            System.out.println(PROGRAM_DESCRIPTION);
            commander.usage();
            System.exit(0);
        }

        if (commonOptions.isDebug()) {
            val root = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
            root.setLevel(DEBUG);
        }
    }

    private static String getProgramName () {
        val name = new File(CommandLineParser.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getName();

        return name.contains(".jar")
               ? name
               : "ping.jar";
    }

    private CommandLineParser () {
    }
}
