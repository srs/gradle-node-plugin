package com.moowork.gradle.node

import com.moowork.gradle.AbstractProjectTest

class NodeExtensionTest
    extends AbstractProjectTest
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
        ext.version == '10.15.3'
        !ext.download
        ext.npmVersion == ''
    }
}
