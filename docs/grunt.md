# Grunt Plugin

This is a very simple Gradle plugin for running [Grunt](http://gruntjs.com/) tasks part of the build. It merely 
wraps calls to `grunt xyz` as `gradle grunt_xyz` tasks. Grunt is installed locally using npm. To 
start using the plugin, add this into your `build.gradle` file (see [Installing](installing.md) for details):

```gradle
plugins {
  id "com.moowork.grunt" version "1.2.0"
}
```

The plugin will also automatically apply the [Node Plugin](node-plugin.md).


## Using the plugin

You can run grunt tasks using this syntax:

```bash
$ gradle grunt_build    # this runs grunt build
$ gradle grunt_compile  # this runs grunt compile
```

... and so on.

These tasks do not appear explicitly in `gradle tasks`, they only appear as task rule.
Your Gruntfile.js defines what grunt_* tasks exist (see `grunt --help`, or `gradle grunt_--help`).

Also (more importantly), you can depend on those tasks, e.g.

```gradle
// runs "grunt build" as part of your gradle build
build.dependsOn grunt_build
```

This is the main advantage of this plugin, to allow build scripts (and grunt agnostics) to run grunt 
tasks via gradle.

It is also possible to run a grunt task only if one of its input files have changed:

```gradle
def srcDir = new File(projectDir, "src/main/web")
def targetDir = new File(project.buildDir, "web")
grunt_dist.inputs.dir srcDir
grunt_dist.outputs.dir targetDir
```


## Extended Usage

If you need to supply grunt with options, you have to create Grunt-tasks:

```gradle
task gruntBuildWithOpts(type: GruntTask) {
  args = ["build", "arg1", "arg2"]
}
```


## NPM helpers

The plugin also provides two fixed helper tasks to run once per project, which
however require npm (https://npmjs.org/) to be installed:

 - `installGrunt`: This installs grunt and grunt-cli to the project folder, using `npm install grunt grunt-cli`
 - `npmInstall`: This just runs `npm install` (possibly useful for scripting)

Since grunt will only be installed in your project folder, it will not interact with the rest of your 
system, and can easily be removed later by deleting the node_modules folders.

So as an example, you can make sure a local version of grunt exists using this:

```gradle
// makes sure on each build that grunt is installed
grunt_build.dependsOn 'installGrunt'

// processes your package.json before running grunt build
grunt_build.dependsOn 'npmInstall'

// runs "grunt build" as part of your gradle build
build.dependsOn grunt_build
```


## Configuring the Plugin

You can configure the plugin using the `grunt` extension block, like this:

```gradle
grunt {
  // Set the directory where Gruntfile.js should be found
  workDir = file("${project.projectDir}")

  // Whether colors should output on the terminal
  colors = true

  // Whether output from Grunt should be buffered - useful when running tasks in parallel
  bufferOutput = false
    
  // Use different gruntFile
  gruntFile = 'Gruntfile.js'
}
```
