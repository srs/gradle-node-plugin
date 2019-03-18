# Installing

Installing the node-related plugins can be done in multiple ways. The easiest is to use the `plugins`-closure 
in your `build.gradle` file:

```gradle
plugins {
  id "com.moowork.node" version "1.3.1"
}
```

If you want to install all of the node-plugins (which is pretty uncommon), then write this:

```gradle
plugins {
  id "com.moowork.node" version "1.3.1"
  id "com.moowork.grunt" version "1.3.1"
  id "com.moowork.gulp" version "1.3.1"
}
```

You can also install the plugins by using the traditional Gradle way:

```gradle
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    } 
  }

  dependencies {
    classpath "com.moowork.gradle:gradle-node-plugin:1.3.1"
  }
}

apply plugin: 'com.moowork.node'
```


## Installing snapshots

If you want to install snapshot versions of this plugin, you can add the [OJO repository](http://oss.jfrog.org)
to your build:

```gradle
buildscript {
  repositories {
    maven {
      url "http://oss.jfrog.org"
    } 
  }

  dependencies {
    classpath "com.moowork.gradle:gradle-node-plugin:1.4.0-SNAPSHOT"
  }
}

apply plugin: 'com.moowork.node'
```
