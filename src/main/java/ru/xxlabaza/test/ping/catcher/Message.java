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

import static lombok.AccessLevel.PRIVATE;

import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.val;

/**
 * Message abstraction for holding different byte buffers.
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 28.06.2017
 */
@Getter
@FieldDefaults(level = PRIVATE, makeFinal = true)
class Message {

    ByteBuffer size;

    ByteBuffer id;

    ByteBuffer body;

    ByteBuffer[] buffers;

    Message () {
        size = ByteBuffer.allocate(2);
        id = ByteBuffer.allocate(8);
        body = ByteBuffer.allocate(2990);
        buffers = new ByteBuffer[] { size, id, body };
    }

    @Override
    public String toString () {
        val result = new StringBuilder('\n');

        size.mark();
        result.append("size: ").append(size.getShort(0)).append('\n');
        size.reset();

        id.mark();
        result.append("id: ").append(id.getLong(0)).append('\n');
        id.reset();

        val to = body.position() != 0
                 ? body.position()
                 : body.limit();

        val array = Arrays.copyOfRange(body.array(), 0, to);
        val hexString = DatatypeConverter.printHexBinary(array);
        return result.append("body: ").append(hexString).toString();
    }

    boolean hasRemaining () {
        return size.hasRemaining() || id.hasRemaining() || body.hasRemaining();
    }

    boolean isFilled () {
        if (size.hasRemaining()) {
            return false;
        }

        size.mark();
        short sizeCache = size.getShort(0);
        size.reset();

        return sizeCache == size.position() + id.position() + body.position();
    }

    void flip () {
        size.flip();
        id.flip();
        body.flip();
    }

    void clear () {
        size.clear();
        id.clear();
        body.clear();
    }
}
