Changelog
=========

Version 0.7 *(not released)*
----------------------------

* Allow local npm to override bundled npm _(dougborg)_
* Allow for configuring npmVersion _(dougborg)_
* ...

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

