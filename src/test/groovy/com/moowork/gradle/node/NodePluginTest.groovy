package com.moowork.gradle.node

import nebula.test.ProjectSpec

class NodePluginTest
    extends ProjectSpec
{
    def 'check default tasks'()
    {
        when:
        this.project.apply plugin: 'com.moowork.node'

        then:
        this.project.extensions.getByName( 'node' )
        this.project.tasks.getByName( 'nodeSetup' )
        this.project.tasks.getByName( 'npmInstall' )
        this.project.tasks.getByName( 'npmSetup' )
    }
}
