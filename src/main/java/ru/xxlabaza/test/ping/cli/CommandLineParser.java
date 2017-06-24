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
import ru.xxlabaza.test.ping.localization.UTF8Control;

/**
 * Command line arguments parser utility class.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 24.06.2017
 */
public final class CommandLineParser {

    public static void parse (String[] args) throws ParameterException {
        val bundle = ResourceBundle.getBundle("localization/options", new UTF8Control());
        val programName = "java -jar " + getProgramName();

        val commonOptions = new CommonOptions();

        val commander = JCommander.newBuilder()
                .programName(programName)
                .addObject(commonOptions)
                .resourceBundle(bundle)
                .build();

        try {
            commander.parse(args);
        } catch (ParameterException ex) {
            val argumentErrorPrefix = bundle.getString("program.argument.parse.error");
            System.err.format(argumentErrorPrefix, ex.getMessage());
            commander.usage();
            throw ex;
        }

        if (commonOptions.isHelp()) {
            val programDescription = bundle.getString("program.description");
            System.out.println(programDescription);
            commander.usage();
            return;
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
