Changelog
=========

Version 1.0.2 *(not released)*
------------------------------

* Override environment instead of setting it (#176) _(s0x)_
* Fix typo in resolveSingle (#166) _(s0x)_
* ...

Version 1.0.1 *(2016-12-04)*
----------------------------

* Publish directly to plugins.gradle.org instead of bintray (#172)
* Fixed problem with resolving Grunt and Gulp plugins (#170)

Version 1.0.0 *(2016-12-02)*
----------------------------

* Move npm and yarn classes into separate package (#158)
* Move grunt plugin code to this plugin (#159)
* Move gulp plugin code to this plugin (#160)
* Use 6.9.1 as default node version (#163)
* Fix missing property exception when using plugin in conjunction with Node 6.x.x on Windows (#167) _(mark-vieira)_
* Switch over to use semantic versioning (#169)

Version 0.14 *(2016-11-29)*
---------------------------

* Bumped gradle wrapper version to 3.2.1
* Use gradle-testkit instead of nebula (#153)
* Update to use Windows zip dists when available (#142) _(datallah)_
* Added support for Yarn (#145, #151) _(kaitoy)_

Version 0.13 *(2016-06-27)*
---------------------------

* Bumped gradle wrapper version to 2.14 
* Implement ARM compatibility _(madmas)_
* Allow node modules to be used when calling npm_run _(jmcampanini)_
* Updated plugin versions and test versions
* Node.workingDir set to nodeModulesDir (fixes #107)
* Creates nodeModulesDir if it does not exist (fixes #108)
* Use single repo for node download (fixes #120)

Version 0.12 *(2016-03-10)*
---------------------------

* Updated wrapper to use Gradle 2.11
* Refactored windows-specific logic for npm _(peol)_
* Use temporary repository for resolving node dependencies
* Using 4.4.0 (latest LTS) as default node version
* Changed default workDir location to be local to project directory

Version 0.11 *(2015-09-26)*
---------------------------

* Handle 4+ nodejs releases on windows _(dvaske)_
* Add npmCommand parameter to the node extension _(janrotter)_
* Set executable flag on node in SetupTask
* Upgraded wrapper to use Gradle 2.7
* Update node distribution base url to use https _(AvihayTsayeg)_
* Added more tests (unit, integration and functional tests)
* NodeTask environment is now correctly propagated to the runner

Version 0.10 *(2015-05-19)*
---------------------------

* Fixed problem with spaces in workDir
* Add configuration for node_modules location _(nmalaguti)_
* Solaris support _(halfninja)_
* Upgraded wrapper to use Gradle 2.4

Version 0.9 *(2015-02-28)*
--------------------------

* Updated some plugin references
* Fixed some tests on Windows _(abelsromero)_
* Fixed issue for windows environments, not containing "PATH" but "Path" _(tspaeth)_
* Allow 64 bit windows to use the x64 node.exe _(ekaufman)_
* Renamed "ext" property on SetupTask so that it's not causing any conflicts
* Added detection for FreeBSD as a Linux variant
* Compiling using Java 1.6 compatiblity _(stianl)_

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
