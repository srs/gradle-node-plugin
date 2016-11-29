Gradle Plugin for Node
=======================

[![Build Status](https://travis-ci.org/srs/gradle-node-plugin.svg?branch=master)](https://travis-ci.org/srs/gradle-node-plugin)
[![Windows Build status](https://ci.appveyor.com/api/projects/status/06pg08c36mnes0w3?svg=true)](https://ci.appveyor.com/project/srs/gradle-node-plugin)
[![License](https://img.shields.io/github/license/srs/gradle-node-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Download](https://api.bintray.com/packages/srs/maven/gradle-node-plugin/images/download.svg) ](https://bintray.com/srs/maven/gradle-node-plugin/_latestVersion)

This plugin enabled you to use a lot of [NodeJS](https://nodejs.org)-based technologies as part of your 
build without having NodeJS installed locally on your system.

<img height="120" src="https://raw.githubusercontent.com/srs/gradle-node-plugin/master/docs/assets/nodejs.png">
<img height="120" src="https://raw.githubusercontent.com/srs/gradle-node-plugin/master/docs/assets/grunt.png">
<img height="120" src="https://raw.githubusercontent.com/srs/gradle-node-plugin/master/docs/assets/gulp.png">
<img height="120" src="https://raw.githubusercontent.com/srs/gradle-node-plugin/master/docs/assets/yarn.png">




This plugin enables you to run any NodeJS script as part of your build. It does
not depend on NodeJS (or NPM) being installed on your system. The plugin will
download and manage NodeJS distributions, unpack them into your local `.gradle`
directory and use them from there.

It also 

Installing the Plugin
---------------------

Releases of this plugin are hosted at [Bintray][] and is part of the [jCenter
repository][]. Development builds are published for every commit to the master
branch. These `SNAPSHOT`s are hosted on the [OJO repository][] and to use them
you will need to add OJO to your `buildscript` configuration.

[Bintray]: http://bintray.com
[OJO repository]: http://oss.jfrog.org
[jCenter repository]: https://bintray.com/bintray/jcenter

Setup the plugin like this:

    plugins {
      id "com.moowork.node" version "0.14"
    }

Or:

    buildscript {
        repositories {
            maven {
                url "https://plugins.gradle.org/m2/"
            }
        }

        dependencies {
            classpath "com.moowork.gradle:gradle-node-plugin:0.14"
        }
    }

    apply plugin: 'com.moowork.node'


Running a NodeJS Script
-----------------------

To use this plugin you have to define some tasks in your build.gradle file. If
you have a NodeJS script in `src/scripts/my.js`, then you can execute this by
defining the following Gradle task:

    task myScript(type: NodeTask) {
      script = file('src/scripts/my.js')
    }

You can also add arguments, like this:

    task myScript(type: NodeTask) {
      script = file('src/scripts/my.js')
      args = ['arg1', 'arg2']
    }

`NodeTask` is a wrapper around the core `Exec` task. You can set the
`ignoreExitValue` property on it:

    task myScript(type: NodeTask) {
      script = file('src/scripts/my.js')
      ignoreExitValue = true
    }

You can also customize all other values on the `ExecSpec` by passing a closure
to `execOverrides`. It's executed last, possibly overriding already set
parameters.

    task myScript(type: NodeTask) {
      script = file('src/scripts/my.js')
      execOverrides {
        it.ignoreExitValue = true
        it.workingDir = 'somewhere'
        it.standardOutput = new FileOutputStream('logs/my.log')
      }
    }

When executing this for the first time, it will run a nodeSetup task that
downloades NodeJS (for your platform) and NPM (Node Package Manager) if on
windows (other platforms include it into the distribution).

Executing `npm` Tasks
---------------------

When adding the node plugin, you will have a npmInstall task already added. This
task will execute `npm install` and installs all dependencies in `package.json`.
It will only run when changes are made to `package.json` or `node_modules`.
Execute it like this:

    $ gradle npmInstall

All npm command can also be invoked using underscore notation based on a gradle
rule:

    $ gradle npm_install
    $ gradle npm_update
    $ gradle npm_list
    $ gradle npm_cache_clean
    ...

These however are not shown when running gradle tasks, as they generated
dynamically. However they can be used for dependency declarations, such as:

    npm_install.dependsOn(npm_cache_clean)

More arguments can be passed via the build.gradle file:

    npm_update {
      args = ['--production', '--loglevel', 'warn']
    }

If you want to extend the tasks more or create custom variants, you can extend
the class `NpmTask`:

    task installExpress(type: NpmTask) {
      // install the express package only
      args = ['install', 'express', '--save-dev']
    }

Executing Yarn Tasks
---------------------

When adding the node plugin, you will have a yarn task already added. This
task will execute `yarn` and installs all dependencies in `package.json`.
It will only run when changes are made to `package.json`, `yarn.lock`, or `node_modules`.
Execute it like this:

    $ gradle yarn

All npm command can also be invoked using underscore notation based on a gradle
rule:

    $ gradle yarn_install
    $ gradle yarn_upgrade
    $ gradle yarn_ls
    $ gradle yarn_cache_clean
    ...

These however are not shown when running gradle tasks, as they generated
dynamically. However they can be used for dependency declarations, such as:

    yarn_install.dependsOn(yarn_cache_clean)

More arguments can be passed via the build.gradle file:

    yarn_cache_clean {
      args = ['--no-emoji', '--json']
    }

If you want to extend the tasks more or create custom variants, you can extend
the class `YarnTask`:

    task addExpress(type: YarnTask) {
      // add the express package only
      args = ['add', 'express', '--dev']
    }

Configuring the Plugin
----------------------

You can configure the plugin using the "node" extension block, like this:

    node {
      // Version of node to use.
      version = '0.11.10'

      // Version of npm to use.
      npmVersion = '2.1.5'

      // Version of Yarn to use.
      yarnVersion = '0.16.1'

      // Base URL for fetching node distributions (change if you have a mirror).
      distBaseUrl = 'https://nodejs.org/dist'

      // If true, it will download node using above parameters.
      // If false, it will try to use globally installed node.
      download = true

      // Set the work directory for unpacking node
      workDir = file("${project.buildDir}/nodejs")

      // Set the work directory for NPM
      npmWorkDir = file("${project.buildDir}/npm")

      // Set the work directory for Yarn
      yarnWorkDir = file("${project.buildDir}/yarn")

      // Set the work directory where node_modules should be located
      nodeModulesDir = file("${project.projectDir}")
    }

**Note** that `download` flag is default to `false`. This will change in future versions.

Using a Custom (project-local) Version of `npm`
-----------------------------------------------

If `npmVersion` is specified, the plugin installs that version of `npm` into `npmWorkDir`
by the `npmSetup` task and use it.

If `npmVersion` is not specified and a locally-installed `npm` exists, The plugin will
use it.

Otherwise, the plugin will use the `npm` bundled with the version of node installation.

Using a Custom (project-local) Version of `yarn`
-----------------------------------------------

The plugin never uses a locally-installed `yarn` because it may be deleted during
`yarn` execution.
Instead, it installs `yarn` into `yarnWorkDir` (`.gradle/yarn/` by default) by
the `yarnSetup` task and use it.

If you would like the plugin to install use a custom version of yarn, you can set
`yarnVersion` in the `node` extension block.

Building the Plugin
-------------------

To build the plugin, just type the following command:

    ./gradlew clean build
