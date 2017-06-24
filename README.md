
# Ping

A client-server program for determining network round trip time.

## Getting Started

Print help information:

```bash
$> java -jar ping-0.0.1.jar --help

Client-server program for determining network round trip time.

Usage: java -jar ping-0.0.1.jar [options]
  Options:
    --debug, -d
      Setup ROOT log level to DEBUG.
      Default: false
    --help, -h
      Print usage information.

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
