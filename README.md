Gradle plugin for Node
=======================

This plugin enables you to run any NodeJS script as part of your build. It does not depend on NodeJS (or NPM) being installed on
your system. Fist time it will download NodeJS distribution and unpack it into your local .gradle directory and run it from there.

Status
------

* Build: [![Build Status](https://travis-ci.org/srs/gradle-node-plugin.png?branch=master)](https://travis-ci.org/srs/gradle-node-plugin)
* Download: [![Download](https://api.bintray.com/packages/srs/maven/gradle-node-plugin/images/download.png)](https://bintray.com/srs/maven/gradle-node-plugin)
* License: [![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Installing the plugin
---------------------

Releases of this plugin are hosted at BinTray (http://bintray.com) and is part of jcenter repository.
Setup the plugin like this:

	buildscript {
		repositories {
			jcenter()
		}
    	dependencies {
			classpath 'com.moowork.gradle:gradle-node-plugin:0.6'
    	}
	}

Include the plugin in your build.gradle file like this:

    apply plugin: 'com.moowork.node'

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

`NodeTask` is a wrapper around the core `Exec` task. You can set the `ignoreExitValue` property on it:

    task myScript(type: NodeTask) {
       script = file('src/scripts/my.js')
       ignoreExitValue = true
    }

You can also customize all other values on the `ExecSpec` by passing a closure to `execOverrides`. It's executed last, possibly
overriding already set parameters.

    task myScript(type: NodeTask) {
       script = file('src/scripts/my.js')
       execOverrides {
           it.ignoreExitValue = true
           it.workingDir = 'somewhere'
           it.standardOutput = new FileOutputStream('logs/my.log')
       }
    }

When executing this for the first time, it will run a nodeSetup task that downloades NodeJS (for your platform) and
NPM (Node Package Manager) if on windows (other platforms include it into the distribution).

Executing NPM tasks
-------------------

When adding the node plugin, you will have a npmInstall task already added. This task will execute "npm install" and
installs all dependencies in "package.json". It will only run when changes are made to package.json or node_modules.
 Execute it like this:

    $ gradle npmInstall

All npm command can also be invoked using underscore notation based on a gradle rule

     $ gradle npm_install
     $ gradle npm_update
     $ gradle npm_list
     $ gradle npm_cache_clean
     ...

These however are not shown when running gradle tasks, as they generated dynamically.
However they can be used for dependency declarations, such as npm_install.dependsOn(npm_cache_clean)
More arguments can be passed via the build.gradle file:

    npm_update {
        args = ['--production', '--loglevel', 'warn']
    }

If you want to extend the tasks more or create custom variants, you can extend the class npmTask:

    task installExpress(type: NpmTask) {
        // install the express package only
        args = ['install', 'express', '--save-dev']
    }

Configuring the plugin
----------------------

You can configure the plugin using the "node" extension block, like this:

    node {
        // Version of node to use.
        version = '0.11.10'

        // Base URL for fetching node distributions (change if you have a mirror).
        distBaseUrl = 'http://nodejs.org/dist'

        // If true, it will download node using above parameters.
        // If false, it will try to use globally installed node.
        download = true

        // Set the work directory for unpacking node
        workDir = file("${project.buildDir}/nodejs")
    }

Using a custom (project-local) version of npm
---------------------------------------------

If you are using the plugin to manage your node installation (`download = true`)
and would like to use a custom version of npm rather than the one bundled with
the version of node you have configured, you may install it locally using an
`NpmTask`:

    task installCustomNpm(type: NpmTask) {
      args = ['install', 'npm@2.1.1']
    }

The task above will install the npm package to the project's `node_modules`
directory and the plugin will use a locally-installed npm if it exists, and
subsequent`NpmTask`s will use the version of npm installed above.

Building the Plugin
-------------------

To build the plugin, just type the following command:

    ./gradlew clean build
