# Changes from 0.x to 1.0

### Node Plugin

* `NodeExtension.workDir` is renamed to `cacheDir`. This describes what it is much better since it's actually where the node/npm installation is unpacked (and cached).
* `NodeExtension.nodeModulesDir` is renamed to `workDir`. This makes it consistent with other tasks which call the same concept a *"working directory"*.
* `NodeExtension.download` is now default set to `true` instead of `false`.
