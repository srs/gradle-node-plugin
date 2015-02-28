Changelog
=========

Version 0.9 *(not released)*
----------------------------

* Updated some plugin references
* Fixed some tests on Windows _(abelsromero)_
* Fixed issue for windows environments, not containing "PATH" but "Path" _(tspaeth)_
* Allow 64 bit windows to use the x64 node.exe _(ekaufman)_
* Renamed "ext" property on SetupTask so that it's not causing any conflicts
* Added detection for FreeBSD as a Linux variant
* Compiling using Java 1.6 compatiblity _(stianl)_
* ...

Version 0.8 *(2014-11-19)*
--------------------------

* Publish snapshots to jcenter _(dougborg)_
* Add node to execution path for NodeExec _(dougborg)_
* Use 'com.moowork.node' id instead of 'node
* Upgraded wrapper to use Gradle 2.2

Version 0.7 *(2014-11-03)*
--------------------------

* Allow local npm to override bundled npm _(dougborg)_
* Allow for configuring npmVersion _(dougborg)_
* Upgrade to Gradle 2.1

Version 0.6 *(2014-07-10)*
--------------------------

* Upgrade to Gradle 2.0
* Using 'com.moowork.node' as plugin id, but 'node' still works for another version
* Possible to read execResult for npm and node tasks _(johnrengelman)_

Version 0.5 *(2014-03-29)*
--------------------------

* Upgraded to use Node version 0.10.22 _(tkruse)_
* Provide gradle rule to run any NPM commands _(tkruse)_

Version 0.4 *(2014-03-07)*
--------------------------

* Possible to ignoreExitValue _(konrad-garus)_
* Added support for exec taks overrides (delegates down to Gradle exec task) _(konrad-garus)_
* Now adding npmInstall outside afterEvaluate to allow better dependsOn usage
* Reworked SetupTask so that it is using task input/output change tracking
* Updated gradle wrapper to version 1.11

Version 0.3 *(2014-01-10)*
--------------------------

* Initial usable version
