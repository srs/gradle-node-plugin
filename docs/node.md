# Node Plugin

This plugin enables you to run any [NodeJS](https://nodejs.org) script as part of your build. It does
not depend on NodeJS (or NPM) being installed on your system. The plugin will
download and manage NodeJS distributions, unpack them into your local `.gradle`
directory and use them from there. It can also install [NPM](https://www.npmjs.com/) 
packages from NPM or [Yarn](https://yarnpkg.com/). To start using the plugin, add this into your `build.gradle` 
file (see [Installing](installing.md) for details):


```gradle
plugins {
  id "com.moowork.node" version "1.0.1"
}
```

## Running a NodeJS Script

To use this plugin you have to define some tasks in your `build.gradle` file. If you have a NodeJS 
script in `src/scripts/my.js`, then you can execute this by defining the following Gradle task:

```gradle
task myScript(type: NodeTask) {
  script = file('src/scripts/my.js')
}
```

You can also add arguments, like this:

```gradle
task myScript(type: NodeTask) {
  script = file('src/scripts/my.js')
  args = ['arg1', 'arg2']
}
```

You can add node options like this:

```gradle
task myScript(type: NodeTask) {
  script = file('src/scripts/my.js')
  options = ['--node-option', '--another-node-option']
}
```

`NodeTask` is a wrapper around the core `Exec` task. You can set the `ignoreExitValue` property on it:

```gradle
task myScript(type: NodeTask) {
  script = file('src/scripts/my.js')
  ignoreExitValue = true
}
````

You can also customize all other values on the `ExecSpec` by passing a closure to `execOverrides`. It's 
executed last, possibly overriding already set parameters.

```gradle
task myScript(type: NodeTask) {
  script = file('src/scripts/my.js')
  execOverrides {
    it.ignoreExitValue = true
    it.workingDir = 'somewhere'
    it.standardOutput = new FileOutputStream('logs/my.log')
  }
}
```

When executing this for the first time, it will run a nodeSetup task that downloads NodeJS 
(for your platform) and NPM (Node Package Manager) if on windows (other platforms include 
it into the distribution).


## Executing `npm` Tasks

When adding the node plugin, you will have a `npmInstall` task already added. This task will 
execute `npm install` and installs all dependencies in `package.json`. It will only run when changes 
are made to `package.json` or `node_modules`. Execute it like this:

```bash
$ gradle npmInstall
```

All npm command can also be invoked using underscore notation based on a gradle rule:

```bash
$ gradle npm_install
$ gradle npm_update
$ gradle npm_list
$ gradle npm_cache_clean
...
```

These however are not shown when running gradle tasks, as they generated dynamically. However they can 
be used for dependency declarations, such as:

```gradle
npm_install.dependsOn(npm_cache_clean)
```

More arguments can be passed via the `build.gradle` file:

```gradle
npm_update {
  args = ['--production', '--loglevel', 'warn']
}
```

If you want to extend the tasks more or create custom variants, you can extend the class `NpmTask`:

```gradle
task installExpress(type: NpmTask) {
  // install the express package only
  args = ['install', 'express', '--save-dev']
}
```


## Executing Yarn Tasks

When adding the node plugin, you will have a yarn task already added. This task will 
execute `yarn` and installs all dependencies in `package.json`. It will only run when changes 
are made to `package.json`, `yarn.lock`, or `node_modules`. Execute it like this:

```bash
$ gradle yarn
```

All npm command can also be invoked using underscore notation based on a gradle rule:

```bash
$ gradle yarn_install
$ gradle yarn_upgrade
$ gradle yarn_ls
$ gradle yarn_cache_clean
...
```

These however are not shown when running gradle tasks, as they generated dynamically. However they can be 
used for dependency declarations, such as:

```gradle
yarn_install.dependsOn(yarn_cache_clean)
```

More arguments can be passed via the `build.gradle` file:

```gradle
yarn_cache_clean {
  args = ['--no-emoji', '--json']
}
```

If you want to extend the tasks more or create custom variants, you can extend the class `YarnTask`:

```gradle
task addExpress(type: YarnTask) {
  // add the express package only
  args = ['add', 'express', '--dev']
}
```


## Configuring the Plugin

You can configure the plugin using the "node" extension block, like this:

```gradle
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
```

**Note** that `download` flag is default to `false`. This will change in future versions.


## Using a Custom (project-local) Version of `npm`

If `npmVersion` is specified, the plugin installs that version of `npm` into `npmWorkDir`
by the `npmSetup` task and use it.

If `npmVersion` is not specified and a locally-installed `npm` exists, The plugin will
use it.

Otherwise, the plugin will use the `npm` bundled with the version of node installation.


## Using a Custom (project-local) Version of `yarn`

The plugin never uses a locally-installed `yarn` because it may be deleted during
`yarn` execution.
Instead, it installs `yarn` into `yarnWorkDir` (`.gradle/yarn/` by default) by
the `yarnSetup` task and use it.

If you would like the plugin to install use a custom version of yarn, you can set
`yarnVersion` in the `node` extension block.
