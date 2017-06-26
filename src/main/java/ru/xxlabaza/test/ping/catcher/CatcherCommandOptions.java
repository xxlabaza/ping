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
package ru.xxlabaza.test.ping.catcher;

import static ru.xxlabaza.test.ping.catcher.CatcherCommandOptions.NAME;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.InetAddressConverter;
import java.net.InetAddress;
import lombok.Getter;
import lombok.ToString;
import ru.xxlabaza.test.ping.cli.GreaterThanZeroValidator;

/**
 * Catcher command specific options holder.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 26.06.2017
 */
@Getter
@ToString
@Parameters(
        commandNames = NAME,
        commandDescriptionKey = "catcher.command.description"
)
public class CatcherCommandOptions {

    public final static String NAME = "-c";

    @Parameter(
            names = "-port",
            descriptionKey = "catcher.command.option.port",
            arity = 1,
            required = true,
            validateWith = GreaterThanZeroValidator.class
    )
    private Integer port;

    @Parameter(
            names = "-bind",
            descriptionKey = "catcher.command.option.bind",
            converter = InetAddressConverter.class,
            arity = 1,
            required = true
    )
    private InetAddress bind;
}
