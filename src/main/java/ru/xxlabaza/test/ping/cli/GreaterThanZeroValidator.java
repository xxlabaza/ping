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

package ru.xxlabaza.test.ping.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import lombok.val;
import ru.xxlabaza.test.ping.localization.I18nExceptionUtil;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 25.06.2017
 */
public class GreaterThanZeroValidator implements IParameterValidator {

    @Override
    public void validate (String name, String value) throws ParameterException {
        val number = Integer.parseInt(value);
        if (number > 0) {
            return;
        }

        val message = I18nExceptionUtil.getMessage("cli.validation.GreaterThanZero", name, value);
        throw new ParameterException(message);
    }
}
