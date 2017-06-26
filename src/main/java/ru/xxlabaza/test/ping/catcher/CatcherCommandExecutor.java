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

import java.net.ServerSocket;
import java.util.concurrent.Executors;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.SneakyThrows;
import lombok.val;
import lombok.Value;
import ru.xxlabaza.test.ping.CommandExecutor;

/**
 * The class represents a catcher command.
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

        val serverSocket = new ServerSocket(options.getPort(), 0, options.getBind());
        val executor = Executors.newCachedThreadPool();
        while (true) {
            val socket = serverSocket.accept();
            val listener = new EchoListener(socket);
            executor.submit(listener);
        }
    }
}
