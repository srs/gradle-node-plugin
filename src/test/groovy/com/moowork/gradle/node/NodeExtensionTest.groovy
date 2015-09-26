package com.moowork.gradle.node

import nebula.test.ProjectSpec

class NodeExtensionTest
    extends ProjectSpec
{
    def "check default values for extension"()
    {
        when:
        this.project.apply plugin: 'com.moowork.node'
        def ext = NodeExtension.get( this.project )

        then:
        ext != null
        ext.npmCommand == 'npm'
        ext.distBaseUrl == 'https://nodejs.org/dist'
        ext.workDir != null
        ext.nodeModulesDir != null
        ext.version == '0.11.10'
        !ext.download
        ext.npmVersion == ''
    }
}
