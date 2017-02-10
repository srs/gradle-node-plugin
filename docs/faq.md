# FAQ

This page contains a collection of frequently asked questions.


# How to avoid node/npm/yarn task execution if no changes in web files?

Just add to your bundle task filesets (in and out) wich this task depends on:

```js
task bundle(type: YarnTask) {
    inputs.files(fileTree('node_modules'))
    inputs.files(fileTree('src'))
    inputs.file('package.json')
    inputs.file('webpack.config.js')
    
    outputs.dir('build/resources/static')
 
    dependsOn yarn_install
    args = ['run', 'build']
}
```

More info in [Gradle doc](https://docs.gradle.org/current/userguide/more_about_tasks.html#sec:up_to_date_checks)
