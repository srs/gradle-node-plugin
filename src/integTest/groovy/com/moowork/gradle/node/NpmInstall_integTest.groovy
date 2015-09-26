package com.moowork.gradle.node

import org.gradle.testkit.runner.TaskOutcome

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
        def result = gradleRunner( 'npmInstall' ).build()

        then:
        result.task( ':npmInstall' ).outcome == TaskOutcome.SUCCESS

        when:
        result = gradleRunner( 'npmInstall' ).build()

        then:
        result.task( ':npmInstall' ).outcome == TaskOutcome.UP_TO_DATE
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
        def result = gradleRunner( 'npmInstall' ).build()

        then:
        result.task( ':npmInstall' ).outcome == TaskOutcome.SUCCESS
    }
}
