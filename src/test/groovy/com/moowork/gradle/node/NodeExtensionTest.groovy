package com.moowork.gradle.node

import org.gradle.api.artifacts.repositories.PasswordCredentials

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
        ext.distCredentialsType == PasswordCredentials
        ext.distCredentialsAction == null
        ext.workDir != null
        ext.nodeModulesDir != null
        ext.version == '4.4.0'
        !ext.download
        ext.npmVersion == ''
    }
}
