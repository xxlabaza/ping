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

import com.beust.jcommander.Parameter;
import lombok.Getter;

/**
 * Common parsed program's options holder.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 24.06.2017
 */
@Getter
class CommonOptions {

    @Parameter(
            names = { "--help", "-h" },
            descriptionKey = "common.help",
            help = true
    )
    private boolean help;

    @Parameter(
            names = { "--debug", "-d" },
            descriptionKey = "common.debug"
    )
    private boolean debug;
}
