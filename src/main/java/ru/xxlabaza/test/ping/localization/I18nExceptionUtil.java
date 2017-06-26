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
package ru.xxlabaza.test.ping.localization;

import java.util.ResourceBundle;
import lombok.val;
import org.slf4j.helpers.MessageFormatter;

/**
 * Utility class for working with exception's message bundle.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 25.06.2017
 */
public final class I18nExceptionUtil {

    /**
     * Produces exception message from resource bundle.
     *
     * @param key  message key
     * @param args message additional arguments
     *
     * @return formatted localized string
     */
    public static String getMessage (String key, Object... args) {
        val bundle = ResourceBundle.getBundle("localization/exception", new UTF8Control());
        val messagePattern = bundle.getString(key);
        return MessageFormatter
                .arrayFormat(messagePattern, args)
                .getMessage();
    }

    /**
     * Creates {@link RuntimeException} instance with i18n message.
     *
     * @param key  message key
     * @param args message additional arguments
     *
     * @return localized {@link RuntimeException} instance
     */
    public static RuntimeException throwRuntime (String key, Object... args) {
        val message = getMessage(key, args);
        return new RuntimeException(message);
    }

    private I18nExceptionUtil () {
    }
}
