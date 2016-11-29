package com.moowork.gradle.gulp

import com.moowork.gradle.AbstractProjectTest

class GulpPluginTest
    extends AbstractProjectTest
{
    def 'check default tasks'()
    {
        when:
        this.project.apply plugin: 'com.moowork.gulp'
        this.project.evaluate()

        then:
        this.project.extensions.getByName( 'gulp' )
        this.project.tasks.getByName( 'installGulp' )
    }
}
