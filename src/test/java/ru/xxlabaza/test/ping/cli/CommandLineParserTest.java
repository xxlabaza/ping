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

import static ch.qos.logback.classic.Level.INFO;
import static org.junit.Assert.assertTrue;
import static org.slf4j.Logger.ROOT_LOGGER_NAME;

import ch.qos.logback.classic.Logger;
import com.beust.jcommander.ParameterException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 25.06.2017
 */
public class CommandLineParserTest {

    private static final ByteArrayOutputStream OUT;

    private static final ByteArrayOutputStream ERROR;

    private static Locale defaultLocale;

    static {
        OUT = new ByteArrayOutputStream();
        ERROR = new ByteArrayOutputStream();
    }

    @BeforeClass
    public static void beforeClass () {
        defaultLocale = Locale.getDefault();
    }

    @Before
    public void before () {
        System.setOut(new PrintStream(OUT));
        System.setErr(new PrintStream(ERROR));
    }

    @After
    public void after () {
        System.setOut(null);
        System.setErr(null);

        Logger root = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
        root.setLevel(INFO);

        Locale.setDefault(defaultLocale);
    }

    @Test
    public void testLongHelpUsage () {
        String args[] = { "--help" };
        CommandLineParser.parse(args);

        assertTrue(OUT.toString().contains("Usage:"));
    }

    @Test
    public void testShortHelpUsage () {
        String args[] = { "-h" };
        CommandLineParser.parse(args);

        assertTrue(OUT.toString().contains("Usage:"));
    }

//    @Test
//    public void testLongDebugPrint () {
//        Logger root = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
//        assertEquals(INFO, root.getLevel());
//
//        String args[] = { "--debug" };
//        CommandLineParser.parse(args);
//
//        assertEquals(DEBUG, root.getLevel());
//    }
//
//    @Test
//    public void testShortDebugPrint () {
//        Logger root = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
//        assertEquals(INFO, root.getLevel());
//
//        String args[] = { "-d" };
//        CommandLineParser.parse(args);
//
//        assertEquals(DEBUG, root.getLevel());
//    }
    @Test(expected = ParameterException.class)
    public void testParseException () {
        String args[] = { "-z" };
        CommandLineParser.parse(args);
    }

    @Test
    public void testEnglishLocalization () {
        Locale.setDefault(new Locale("en", "US"));

        String args[] = { "-h" };
        CommandLineParser.parse(args);

        assertTrue(OUT.toString().contains("Client-server program"));
    }

    @Test
    public void testRussianLocalization () {
        Locale.setDefault(new Locale("ru", "RU"));

        String args[] = { "-h" };
        CommandLineParser.parse(args);

        assertTrue(OUT.toString().contains("Клиент-серверная программа"));
    }

    @Test
    public void testDefaultLocalization () {
        Locale.setDefault(new Locale("fr", "FR"));

        String args[] = { "-h" };
        CommandLineParser.parse(args);

        assertTrue(OUT.toString().contains("Client-server program"));
    }
}
