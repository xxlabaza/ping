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
import java.io.File;
import java.util.ResourceBundle;
import lombok.val;
import org.slf4j.LoggerFactory;
import ru.xxlabaza.test.ping.CommandExecutor;
import ru.xxlabaza.test.ping.catcher.CatcherCommandOptions;
import ru.xxlabaza.test.ping.localization.I18nExceptionUtil;
import ru.xxlabaza.test.ping.localization.UTF8Control;
import ru.xxlabaza.test.ping.pitcher.PitcherCommandExecutor;
import ru.xxlabaza.test.ping.pitcher.PitcherCommandOptions;

/**
 * Command line arguments parser utility class.
 * <p>
 * This utility class produces {@link CommandExecutor} instances
 * from user's input.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 24.06.2017
 */
public final class CommandLineParser {

    /**
     * CLI parse method.
     *
     * @param args user's command line arguments
     *
     * @return parsed {@link CommandExecutor} instance or null, if nothing to execute.
     */
    public static CommandExecutor parse (String[] args) {
        val bundle = ResourceBundle.getBundle("localization/options", new UTF8Control());
        val programName = "java -jar " + getProgramName();

        val commonOptions = new CommonOptions();
        val pitcherCommandOptions = new PitcherCommandOptions();
        val catcherCommandOptions = new CatcherCommandOptions();

        val commander = JCommander.newBuilder()
                .programName(programName)
                .resourceBundle(bundle)
                .addObject(commonOptions)
                .addCommand(pitcherCommandOptions)
                .addCommand(catcherCommandOptions)
                .build();

        try {
            commander.parse(args);

            if (commonOptions.isHelp()) {
                val programDescription = bundle.getString("program.description");
                System.out.println(programDescription);
                commander.usage();
                return null;
            } else if (commander.getParsedCommand() == null) {
                throw I18nExceptionUtil.throwRuntime("cli.validation.NoCommand");
            }

            if (commonOptions.isDebug()) {
                val root = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
                root.setLevel(DEBUG);
            }

            switch (commander.getParsedCommand()) {
            case PitcherCommandOptions.NAME:
                return PitcherCommandExecutor.builder()
                        .options(pitcherCommandOptions)
                        .build();
            case CatcherCommandOptions.NAME:
                return null;
            default:
                throw I18nExceptionUtil.throwRuntime("cli.validation.UnknownCommand", commander.getParsedCommand());
            }
        } catch (RuntimeException ex) {
            val argumentErrorPrefix = bundle.getString("program.argument.parse.error");
            System.err.format(argumentErrorPrefix, ex.getMessage());
            commander.usage();
            throw ex;
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
