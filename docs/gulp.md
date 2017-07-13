# Gulp Plugin

This is a very simple Gradle plugin for running [Gulp](http://gulpjs.com/) tasks part of the build.
It merely wraps calls to `gulp xyz` as gradle `gulp_xyz` tasks. Gulp is installed locally using npm.
To start using the plugin, add this into your `build.gradle` file (see [Installing](installing.md) for details):

```gradle
plugins {
  id "com.moowork.gulp" version "1.2.0"
}
```

The plugin will also automatically apply the [Node Plugin](node-plugin.md).


## Using the plugin

You can run gulp tasks using this syntax:

```bash
$ gradle gulp_build    # this runs gulp build
$ gradle gulp_compile  # this runs gulp compile
```

... and so on.

These tasks do not appear explicitly in `gradle tasks`, they only appear as task rule.
Your gulpfile.js defines what gulp_* tasks exist (see `gulp --tasks`, or `gradle gulp_--tasks`).

Also (more importantly), you can depend on those tasks, e.g.

```gradle
// runs "gulp build" as part of your gradle build
build.dependsOn gulp_build
```

This is the main advantage of this plugin, to allow build scripts (and gulp agnostics) to run 
gulp tasks via gradle.

It is also possible to run a gulp task only if one of its input files have changed:

```gradle
def srcDir = new File(projectDir, "src/main/web")
def targetDir = new File(project.buildDir, "web")
gulp_dist.inputs.dir srcDir
gulp_dist.outputs.dir targetDir
```    

## Example `build.gradle` file

To get you started here is what it might look like to integrate this plugin to a Java project.

```gradle
plugins {
  id "com.moowork.gulp" version "1.0"
}
    
apply plugin: "java"

// run npm install
gulp_default.dependsOn 'npmInstall'

// run gulp install
gulp_default.dependsOn 'installGulp'

// processResources is a Java task. Run the gulpfile.js before this task using the 'default' task in the gulpfile.js
processResources.dependsOn gulp_default

// if the /node_modules directory already exists somewhere other than at the base of where your build.gradle is
node {
  nodeModulesDir = file("WebContent")
}
    
// if the /node_modules directory already exists somewhere other than at the base of where your build.gradle is
//  plugin looks for gulp in the node_modules directory
gulp {
  workDir = file("WebContent")
}
```


## Extended Usage

If you need to supply gulp with options, you have to create Gulp-tasks:

```gradle
task gulpBuildWithOpts(type: GulpTask) {
  args = ["build", "arg1", "arg2"]
}
```


## NPM helpers

The plugin also provides two fixed helper tasks to run once per project, which
however require npm (https://npmjs.org/) to be installed:

 - `installGulp`: This installs gulp to the project folder, using `npm install gulp`
 - `npmInstall`: This just runs `npm install` (possibly useful for scripting)

Since gulp will only be installed in your project folder, it will not
interact with the rest of your system, and can easily be removed later by
deleting the node_modules folders.

So as an example, you can make sure a local version of gulp exists using this:

```gradle
// makes sure on each build that gulp is installed
gulp_build.dependsOn 'installGulp'

// processes your package.json before running gulp build
gulp_build.dependsOn 'npmInstall'

// runs "gulp build" as part of your gradle build
build.dependsOn gulp_build
```


## Configuring the Plugin

You can configure the plugin using the `gulp` extension block, like this:

```gradle
gulp {
  // Set the directory where gulpfile.js should be found
  workDir = file("${project.projectDir}")

  // Whether colors should output on the terminal
  colors = true

  // Whether output from Gulp should be buffered - useful when running tasks in parallel
  bufferOutput = false
}
```
