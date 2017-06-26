
# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

[Tags on this repository](https://github.com/xxlabaza/ping/tags)

## [Unreleased]

- Upper bound for **pitcher**'s thread pools and its queues sizes.
- Add more **JUnit** tests.
- Add Netbeans code style files.
- Rewrite **catcher** to NIO.
- Let's Travis build test automatically.

## [1.0.0](https://github.com/xxlabaza/ping/releases/tag/1.0.0) - 2017-06-26

Finally, correct pitcher-to-catcher communication via Sockets.

### Added

- Now, **pitcher** is able to open *Socket* connection and send data (right now - only mesage's id) to remote host.

- Added automatic **Docker** image build for fast deploying on remote host.

- Added async logging in **logback**.

### Changed

- Corrected *logs* bundle with new text.

- Improved README.

## [0.2.0](https://github.com/xxlabaza/ping/releases/tag/0.2.0) - 2017-06-26

Created **catcher** package with necessary functionality.

### Added

- Created **catcher** CLI command with related options.
- Catcher's implementation based on IO and thread pool.

### Changed

- Incremented JAR's version.
- Improved README.

## [0.1.0](https://github.com/xxlabaza/ping/releases/tag/0.1.0) - 2017-06-26

Created **pitcher** package with necessary functionality (except *Socket* connection). Right now this command logs to *STDOUT* its fictional (based on Random class) execution process.

### Added

- Created CommandExecutor interface for **pitcher** and **catcher** (in future) commands implementations.
- Added Pitcher's command options for CLI parsing.
- Created *MessageSenderTask*, which send (not really right now), messages to **catcher** and log it to *Statistic* holder.
- Added *Statistic* class for execution metrics holding.
- Added *StatisticEchoTask* for periodically printing *Statistic* info into log.
- Added *PitcherCommandExecutor* as an *CommandExecutor* implementation for **pitcher** functionality. It Manage *MessageSenderTask* and *StatisticEchoTask* via scheduled thread executors.
- Introduced exception's util class for localization purposes.

### Changed

- Refactored logs/exception/options resource bundles.
- Improved and extended README file.
- Updated project's version.

## [0.0.1](https://github.com/xxlabaza/ping/releases/tag/0.0.1) - 2017-06-24

Basic program's skeleton with CLI and i18n.

### Added
- Command line parser, based on JCommander.
- HELP and DEBUG options, for CLI.
- Localization utility classes for logback logger and ResourceBundle loader.
- Localized properties bundles.
- Logback logger functionality.
- JavaDoc descriptions for all classes.

### Changed
- README.md file has all needed sections and even pictures!
