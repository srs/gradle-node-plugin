Gradle plugin for Node [![Build Status](https://drone.io/github.com/srs/gradle-node-plugin/status.png)](https://drone.io/github.com/srs/gradle-node-plugin/latest)
=======================

This plugin enables you to run any NodeJS script as part of your build. It does not depend on NodeJS (or NPM) being installed on
your system. Fist time it will download NodeJS distribution and unpack it into your local .gradle directory and run it from there.

Installing the plugin
---------------------

Releases of this plugin are hosted at BinTray (http://bintray.com) and is part of jcentral repository.
Setup the plugin like this:

	buildscript {
		repositories {
			jcenter()
		}
    	dependencies {
			classpath 'com.moowork.gradle:gradle-node-plugin:0.2'
    	}
	}

Include the plugin in your build.gradle file like this:

    apply plugin: 'node'

Running a NodeJS script
-----------------------

To use this plugin you have to define some tasks in your build.gradle file. If you have a NodeJS script in src/scripts/my.js, then you
can execute this by defining the following Gradle task:

    task myScript(type: NodeTask) {
        script = file('src/scripts/my.js')
    }

You can also add arguments, like this:

    task myScript(type: NodeTask) {
        script = file('src/scripts/my.js')
        args = ['arg1', 'arg2']
    }

When executing this for the first time, it will run a nodeSetup task that downloades NodeJS (for your platform) and
NPM (Node Package Manager) if on windows (other platforms include it into the distribution).

Executing NPM tasks
-------------------

When adding the node plugin, you will have a npmInstall task already added. This task will execute "npm install" and
installs all dependencies in "package.json". Execute it like this:

    $ grunt npmInstall

If you want to run other NPM commands like installing named modules outside package.json, you can add a custom task like this:

    task installExpress(type: NpmTask) {
        args = ['install', 'express', '--save-dev']
    }

Configuring the plugin
----------------------

You can configure the plugin using the "node" extension block, like this:

    node {
        // Version of node to use.
        version = '0.10.22'

        // Base URL for fetching node distributions (change if you have a mirror).
        distBaseUrl = 'http://nodejs.org/dist'

        // If true, it will download node using above parameters. If false, it will try to use global installed node.
        download = true
    }

Building the Plugin
-------------------

To build the plugin, just type the following commmand:

    ./gradlew clean build
