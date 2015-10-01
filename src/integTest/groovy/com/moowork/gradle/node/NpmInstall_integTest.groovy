package com.moowork.gradle.node

import com.moowork.gradle.AbstractIntegTest

class NpmInstall_integTest
    extends AbstractIntegTest
{
    def 'install packages with npm'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.node'

            node {
                version = "0.10.33"
                npmVersion = "2.1.6"
                download = true
                workDir = file('build/node')
            }
        ''' )
        writeEmptyPackageJson()

        when:
        def result = runTasksSuccessfully( 'npmInstall' )

        then:
        result.wasExecuted( 'npmInstall' )

        when:
        result = runTasksSuccessfully( 'npmInstall' )

        then:
        result.wasUpToDate( 'npmInstall' )
    }

    def 'install packages with npm in different directory'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.node'

            node {
                version = "0.10.33"
                npmVersion = "2.1.6"
                download = true
                workDir = file('build/node')
                nodeModulesDir = file('subdirectory')
            }
        ''' )
        writeFile( 'subdirectory/package.json', """{
            "name": "example",
            "dependencies": {
            }
        }""" )

        when:
        def result = runTasksSuccessfully( 'npmInstall' )

        then:
        result.wasExecuted( 'npmInstall' )
    }
}
