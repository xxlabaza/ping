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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 28.06.2017
 */
public class MessageTest {

    @Test
    public void checkDefaultValues () {
        Message message = new Message();

        assertTrue(message.hasRemaining());
        assertFalse(message.isFilled());
    }

    @Test
    public void testHasRemaining () {
        Message message = new Message();

        message.getSize().putShort((short) 12);
        assertTrue(message.hasRemaining());

        message.getId().putLong(1L);
        assertTrue(message.hasRemaining());

        message.getBody().put((byte) 0xAA);
        assertTrue(message.hasRemaining());

        message.getBody().put((byte) 0xBB);
        assertTrue(message.hasRemaining());
    }

    @Test
    public void testIsFilled () {
        Message message = new Message();

        message.getSize().putShort((short) 12);
        assertFalse(message.isFilled());

        message.getId().putLong(1L);
        assertFalse(message.isFilled());

        message.getBody().put((byte) 0xAA);
        assertFalse(message.isFilled());

        message.getBody().put((byte) 0xBB);
        assertTrue(message.isFilled());
    }
}
