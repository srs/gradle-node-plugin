package com.moowork.gradle.node.npm

import com.moowork.gradle.AbstractIntegTest
import org.gradle.testkit.runner.TaskOutcome

class NpmInstall_integTest
    extends AbstractIntegTest
{
    def 'install packages with npm'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                version = "0.10.33"
                npmVersion = "2.1.6"
                download = true
                workDir = file('build/node')
            }
        ''' )
        writeEmptyPackageJson()

        when:
        def result = buildTask( 'npmInstall' )

        then:
        result.outcome == TaskOutcome.SUCCESS

        when:
        result = buildTask( 'npmInstall' )

        then:
        result.outcome == TaskOutcome.UP_TO_DATE
    }

    def 'install packages with npm and postinstall task requiring npm and node'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                version = "0.10.33"
                npmVersion = "2.1.6"
                download = true
                workDir = file('build/node')
            }
        ''' )
        writePackageJson(""" {
            "name": "example",
            "dependencies": {},
            "versionOutput" : "node --version",
            "postinstall" : "npm run versionOutput"
        }
        """)

        when:
        def result = buildTask( 'npmInstall' )

        then:
        result.outcome == TaskOutcome.SUCCESS

        when:
        result = buildTask( 'npmInstall' )

        then:
        result.outcome == TaskOutcome.UP_TO_DATE
    }

    def 'install packages with npm in different directory'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

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
        def result = build( 'npmInstall' )

        then:
        result.task( ':npmInstall' ).outcome == TaskOutcome.SUCCESS
    }
}
