# Gradle Plugin for Node

[![Build Status](https://travis-ci.org/srs/gradle-node-plugin.svg?branch=master)](https://travis-ci.org/srs/gradle-node-plugin)
[![Windows Build status](https://ci.appveyor.com/api/projects/status/06pg08c36mnes0w3?svg=true)](https://ci.appveyor.com/project/srs/gradle-node-plugin)
[![License](https://img.shields.io/github/license/srs/gradle-node-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Download](https://api.bintray.com/packages/srs/maven/gradle-node-plugin/images/download.svg) ](https://bintray.com/srs/maven/gradle-node-plugin/_latestVersion)

This plugin enabled you to use a lot of [NodeJS](https://nodejs.org)-based technologies as part of your 
build without having NodeJS installed locally on your system. It integrates the following NodeJS-based system
with Gradle:

* [NodeJS](https://nodejs.org)
* [Yarn](https://yarnpkg.com/)
* [Grunt](https://gruntjs.com/)
* [Gulp](https://gulpjs.com/)


## Documentation

Here's how you get started using this plugin. If you do not find what you are looking for, please add an 
issue to [GitHub Issues](https://github.com/srs/gradle-node-plugin/issues).

* [Installing](docs/installing.md)
* [Node Plugin](docs/node-plugin.md)
* [Grunt Plugin](docs/grunt-plugin.md)
* [Gulp Plugin](docs/gulp-plugin.md)
* [FAQ](docs/faq.md)


## Building the Plugin

To build the plugin, just type the following command:

```bash
./gradlew clean build
```
