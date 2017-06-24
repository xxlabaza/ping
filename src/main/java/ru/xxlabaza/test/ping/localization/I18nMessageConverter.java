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
package ru.xxlabaza.test.ping.localization;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.ResourceBundle;
import lombok.val;
import org.slf4j.helpers.MessageFormatter;

/**
 * Custom <link>ClassicConverter</link> implementation for internationalization log messages.
 *
 * @see ClassicConverter
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 24.06.2017
 */
public class I18nMessageConverter extends ClassicConverter {

    private static final ResourceBundle BUNDLE;

    static {
        BUNDLE = ResourceBundle.getBundle("localization/logs", new UTF8Control());
    }

    @Override
    public String convert (ILoggingEvent event) {
        val key = event.getMessage();
        if (!BUNDLE.containsKey(key)) {
            return event.getFormattedMessage();
        }

        val messagePattern = BUNDLE.getString(key);
        val argumentArray = event.getArgumentArray();
        return MessageFormatter
                .arrayFormat(messagePattern, argumentArray)
                .getMessage();
    }
}
