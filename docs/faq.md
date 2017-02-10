# FAQ

This page contains a collection of frequently asked questions.


# How to avoid node/npm/yarn task execution if no changes in web files?

Just add to your bundle task filesets wich this task depends on:

```js
task bundle(type: YarnTask) {
    inputs.files(fileTree('node_modules'))
    inputs.files(fileTree('src'))
    inputs.file('package.json')
    inputs.file('webpack.config.js')
 
    dependsOn yarn_install
    args = ['run', 'build']
}
```
