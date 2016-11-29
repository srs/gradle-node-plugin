package com.moowork.gradle.grunt

import com.moowork.gradle.AbstractProjectTest

class GruntPluginTest
    extends AbstractProjectTest
{
    def 'check default tasks'()
    {
        when:
        this.project.apply plugin: 'com.moowork.grunt'
        this.project.evaluate()

        then:
        this.project.extensions.getByName( 'grunt' )
        this.project.tasks.getByName( 'installGrunt' )
    }
}
