
# Ping

A client-server program for determining network round trip time.

## Overview

**Ping** is a client-server program. The program’s aim is to test the IP network and to determine RTT (Round Trip Time).

The program has two main functioning modes:

* **Pitcher mode** (message thrower) runs on the first, "A", computer and have the functionality of generating and sending messages to another computer.

* **Catcher mode** (message catcher) runs on the second, "B", computer and have the functionality of receiving the message and sending replies to the message sender back.

The output of the program (in **pitcher** mode) shows the following:

* time in HH:mm:ss.SSS format;
* total number of sent messages;

* the number of sent messages in the previous second (speed);

* average time in the last second needed for the message to complete a cycle (A->B->A);

* total maximum time needed for the message to complete a cycle (A->B->A);

* average time for the previous second needed for the message to come from A to B (A-> B);

* average time for the previous second needed for the message to come from B to A (B-> A).

Example of output (**pitcher** mode):

```bash
$> java -jar ping-0.1.0.jar -p -port 9090 localhost
16:36:38.379 INFO  [main] : Pitcher mode enabled
16:36:39.442 INFO  [pool-3-thread-1] :
total send and received messages: 1, the last second 1 msgs/s
A->B->A: max 330ms, A->B->A: avg 330ms, A->B: avg 278ms, B->A: avg 52ms
```

## Getting Started

Print help information:

```bash
$> java -jar ping-0.2.0.jar --help

Client-server program for determining network round trip time.

Usage: java -jar ping-0.2.0.jar [options] [command] [command options]
  Options:
    --debug, -d
      Setup ROOT log level to DEBUG.
      Default: false
    --help, -h
      Print usage information.
  Commands:
    -p      Pitcher mode. The Pitcher (message thrower) runs on the first,
            'A', computer, and have the functionality of generating and
            sending messages to another computer.

      Usage: -p [options] <hostname>
        Options:
          -mps
            The speed of message sending expressed as 'messages per second'.
            Default: 1
        * -port
            TCP socket port used for connecting.
          -size
            Message length, minimum: 50, maximum: 3000.
            Default: 300

    -c      Catcher mode. The Catcher (message catcher) runs on the second,
            'B', computer and have the functionality of receiving the message
            and sending replies to the message sender.

      Usage: -c [options]
        Options:
        * -bind
            TCP socket bind address that will be used to run listen.
        * -port
            TCP socket port used for listening.

```

Starting **catcher** listener:

```bash
$> java -jar ping-0.2.0.jar -c -port 9090 -bind localhost
20:50:57.579 INFO  [main] : Catcher mode enabled
```

Launching the program in **pitcher** mode:

```bash
$> java -jar ping-0.1.0.jar -p -port 9090 localhost
16:36:38.379 INFO  [main] : Pitcher mode enabled
16:36:39.442 INFO  [pool-3-thread-1] :
total send and received messages: 1, the last second 1 msgs/s
A->B->A: max 330ms, A->B->A: avg 330ms, A->B: avg 278ms, B->A: avg 52ms

16:36:40.440 INFO  [pool-3-thread-1] :
total send and received messages: 2, the last second 1 msgs/s
A->B->A: max 504ms, A->B->A: avg 504ms, A->B: avg 411ms, B->A: avg 93ms

16:36:41.440 INFO  [pool-3-thread-1] :
total send and received messages: 3, the last second 1 msgs/s
A->B->A: max 504ms, A->B->A: avg 495ms, A->B: avg 138ms, B->A: avg 357ms

```

### Localization

Right now the programm supports only two languages - Russian and English. By default, it takes system's locale.

Help print in English:

![english help](https://github.com/xxlabaza/ping/blob/master/images/help_english.png?raw=true)

Help print in Russian:

![russian help](https://github.com/xxlabaza/ping/blob/master/images/help_russian.png?raw=true)

Log print in English:

![english log](https://github.com/xxlabaza/ping/blob/master/images/log_english.png?raw=true)

Log print in Russian:

![russian log](https://github.com/xxlabaza/ping/blob/master/images/log_russian.png?raw=true)

## Built With

* [Maven](https://maven.apache.org/) - dependency management
* [JCommander](http://jcommander.org) - small Java framework that makes it trivial to parse command line parameters
* [Lombok](https://projectlombok.org) - is used to reduce boilerplate code
* [Logback](https://logback.qos.ch) - is faster logging systems

## Changelog

To see what has changed in recent versions of Ping, see the [changelog](./CHANGELOG.md) file.

## Contributing

Please read [contributing](./CONTRIBUTING.md) file for details on my code of conduct, and the process for submitting pull requests to me.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/xxlabaza/ping/tags).

## Authors

* **Artem Labazin** - the main creator and developer

## License

This project is licensed under the Apache License 2.0 License - see the [license](./LICENSE) file for details
