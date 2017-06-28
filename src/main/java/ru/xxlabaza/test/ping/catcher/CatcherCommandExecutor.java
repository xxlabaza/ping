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

import static java.nio.channels.SelectionKey.OP_ACCEPT;
import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.xxlabaza.test.ping.CommandExecutor;
import ru.xxlabaza.test.ping.localization.I18nExceptionUtil;

/**
 * The class represents a catcher command.
 * <p>
 * This class starts NIO-based "echo" Socket server.
 *
 * @see CommandExecutor
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 26.06.2017
 */
@Slf4j
@Value
@Builder
public class CatcherCommandExecutor implements CommandExecutor {

    CatcherCommandOptions options;

    @Override
    @SneakyThrows
    public void execute () {
        log.info("catcher.CatcherCommandExecutor.greeting");
        log.debug("catcher.CatcherCommandExecutor.options", options);

        try (val server = ServerSocketChannel.open();
             val selector = Selector.open()) {

            server.socket().setReuseAddress(true);
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(options.getBind(), options.getPort()));
            server.register(selector, OP_ACCEPT);

            while (!Thread.currentThread().isInterrupted()) {
                if (!selector.isOpen()) {
                    Thread.currentThread().interrupt();
                }
                if (selector.select() > 0) {
                    handle(server, selector.selectedKeys());
                }
            }
        } catch (IOException | RuntimeException ex) {
            val exception = I18nExceptionUtil.throwRuntime("catcher.server.UnableToStart", ex.getMessage());
            log.error("catcher.CatcherCommandExecutor.error", exception);
            throw exception;
        }
    }

    private void handle (ServerSocketChannel channel, Set<SelectionKey> keys) throws IOException {
        val iterator = keys.iterator();
        while (iterator.hasNext()) {
            val key = iterator.next();

            if (!key.isValid()) {
                throw I18nExceptionUtil.throwRuntime("catcher.server.InvalidKey");
            }

            if (key.isAcceptable()) {
                log.debug("catcher.CatcherCommandExecutor.accepted");
                accept(channel, key);
                iterator.remove();
            } else if (key.isReadable()) {
                log.debug("catcher.CatcherCommandExecutor.reading");
                if (read(key)) {
                    iterator.remove();
                }
            } else if (key.isWritable()) {
                log.debug("catcher.CatcherCommandExecutor.writing");
                if (write(key)) {
                    iterator.remove();
                }
            } else {
                iterator.remove();
                throw I18nExceptionUtil.throwRuntime("catcher.server.UnknownKeyState");
            }
        }
    }

    private void accept (ServerSocketChannel channel, SelectionKey key) throws IOException {
        val client = channel.accept();
        client.configureBlocking(false);
        client.register(key.selector(), OP_READ, new Message());
    }

    private boolean read (SelectionKey key) throws IOException {
        val client = (SocketChannel) key.channel();
        val message = getMessage(key);
        val bytesRead = client.read(message.getBuffers());
        if (bytesRead < 0 || message.isFilled()) {
            message.flip();
            client.register(key.selector(), OP_WRITE, message);
            return true;
        }
        return false;
    }

    private boolean write (SelectionKey key) throws IOException {
        val message = getMessage(key);
        if (message != null) {
            val client = (SocketChannel) key.channel();

            client.write(message.getBuffers());

            if (message.hasRemaining()) {
                return false;
            }
            log.debug("catcher.CatcherCommandExecutor.debugMessage", message);
            message.clear();
        }

        key.cancel();
        key.channel().close();
        return true;
    }

    private Message getMessage (SelectionKey key) {
        if (!(key.channel() instanceof SocketChannel)) {
            return null;
        }

        val attachment = key.attachment();
        return attachment != null && attachment instanceof Message
               ? (Message) attachment
               : null;
    }
}
