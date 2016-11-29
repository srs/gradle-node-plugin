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

It's actually 3 plugins in one:

* [Node Plugin](docs/node.md) (`com.moowork.node`)
* [Grunt Plugin](docs/grunt.md) (`com.moowork.grunt`)
* [Gulp Plugin](docs/gulp.md) (`com.moowork.gulp`)


## Documentation

Here's how you get started using this plugin. If you do not find what you are looking for, please add an 
issue to [GitHub Issues](https://github.com/srs/gradle-node-plugin/issues).

* [Installing](docs/installing.md)
* [Node Plugin](docs/node.md)
* [Grunt Plugin](docs/grunt.md)
* [Gulp Plugin](docs/gulp.md)
* [Migration](docs/migration.md)
* [FAQ](docs/faq.md)


## Documentation for older releases

Here's the documentation for older releases of the plugin:

* [0.14](https://github.com/srs/gradle-node-plugin/blob/v0.14/README.md)
* [0.13](https://github.com/srs/gradle-node-plugin/blob/v0.13/README.md)
* [0.12](https://github.com/srs/gradle-node-plugin/blob/v0.12/README.md)
* [0.11](https://github.com/srs/gradle-node-plugin/blob/v0.11/README.md)


## Building the Plugin

To build the plugin, just type the following command:

```bash
./gradlew clean build
```
