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

import static ru.xxlabaza.test.ping.pitcher.PitcherCommandOptions.NAME;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.InetAddressConverter;
import java.net.InetAddress;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import lombok.val;
import ru.xxlabaza.test.ping.cli.GreaterThanZeroValidator;
import ru.xxlabaza.test.ping.localization.I18nExceptionUtil;

/**
 * Pitcher command specific options holder.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 25.06.2017
 */
@Getter
@ToString
@Parameters(
        commandNames = NAME,
        commandDescriptionKey = "pitcher.command.description"
)
public class PitcherCommandOptions {

    public final static String NAME = "-p";

    @Parameter(
            names = "-port",
            descriptionKey = "pitcher.command.option.port",
            arity = 1,
            required = true,
            validateWith = GreaterThanZeroValidator.class
    )
    private Integer port;

    @Parameter(
            names = "-mps",
            descriptionKey = "pitcher.command.option.mps",
            arity = 1,
            validateWith = GreaterThanZeroValidator.class
    )
    private int messagePerSecond = 1;

    @Parameter(
            names = "-size",
            descriptionKey = "pitcher.command.option.size",
            arity = 1,
            validateWith = MessageSizeValidator.class
    )
    private int size = 300;

    @Parameter(
            descriptionKey = "pitcher.command.option.hostname",
            converter = InetAddressConverter.class,
            arity = 1,
            required = true
    )
    private List<InetAddress> hostname;

    public InetAddress getHostname () {
        return hostname != null
               ? hostname.get(0)
               : null;
    }

    /**
     * Program's specific option validator for checking generating message size.
     */
    public static class MessageSizeValidator implements IParameterValidator {

        @Override
        public void validate (String name, String value) throws ParameterException {
            val number = Integer.parseInt(value);
            if (number >= 50 && number <= 3000) {
                return;
            }

            val message = I18nExceptionUtil.getMessage("cli.validation.MessageSize", name, value);
            throw new ParameterException(message);
        }
    }
}
