# Gradle Plugin for Node

[![Build Status](https://travis-ci.org/srs/gradle-node-plugin.svg?branch=master)](https://travis-ci.org/srs/gradle-node-plugin)
[![Windows Build status](https://ci.appveyor.com/api/projects/status/06pg08c36mnes0w3?svg=true)](https://ci.appveyor.com/project/srs/gradle-node-plugin)
[![License](https://img.shields.io/github/license/srs/gradle-node-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Download](https://api.bintray.com/packages/srs/maven/gradle-node-plugin/images/download.svg) ](https://bintray.com/srs/maven/gradle-node-plugin/_latestVersion)

> **_NOTE_: 1.0 release is not out yet. Please refer to the 
[current 0.14 release](https://github.com/srs/gradle-node-plugin/blob/v0.14/README.md).**

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
* [0.10](https://github.com/srs/gradle-node-plugin/blob/v0.10/README.md)
* [0.9](https://github.com/srs/gradle-node-plugin/blob/v0.9/README.md)
* [0.8](https://github.com/srs/gradle-node-plugin/blob/v0.8/README.md)
* [0.7](https://github.com/srs/gradle-node-plugin/blob/v0.7/README.md)
* [0.6](https://github.com/srs/gradle-node-plugin/blob/v0.6/README.md)
* [0.5](https://github.com/srs/gradle-node-plugin/blob/v0.5/README.md)
* [0.4](https://github.com/srs/gradle-node-plugin/blob/v0.4/README.md)
* [0.3](https://github.com/srs/gradle-node-plugin/blob/v0.3/README.md)
* [0.2](https://github.com/srs/gradle-node-plugin/blob/v0.2/README.md)


## Building the Plugin

To build the plugin, just type the following command:

```bash
./gradlew clean build
```


## Contributing

Contributions are always welcome! If you'd like to contribute (and we hope you do) please send 
one of the existing contributors a nudge.


## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
