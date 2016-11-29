package com.moowork.gradle.node

import com.moowork.gradle.AbstractIntegTest

class YarnInstall_integTest
    extends AbstractIntegTest
{
    def 'install packages with yarn'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.node'

            node {
                version = "6.9.1"
                yarnVersion = "0.16.1"
                download = true
                workDir = file('build/node')
                yarnWorkDir = file('build/yarn')
            }
        ''' )
        writeEmptyPackageJson()

        when:
        def result = runTasksSuccessfully( 'yarn' )

        then:
        result.wasExecuted( 'yarn' )

        when:
        result = runTasksSuccessfully( 'yarn' )

        then:
        result.wasUpToDate( 'yarn' )
    }

    def 'install packages with yarn in different directory'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.node'

            node {
                version = "6.9.1"
                yarnVersion = "0.15.1"
                download = true
                workDir = file('build/node')
                yarnWorkDir = file('build/yarn')
                nodeModulesDir = file('subdirectory')
            }
        ''' )
        writeFile( 'subdirectory/package.json', """{
            "name": "example",
            "dependencies": {
            }
        }""" )

        when:
        def result = runTasksSuccessfully( 'yarn' )

        then:
        result.wasExecuted( 'yarn' )
    }
}
