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
                npmVersion = "6.1.0"
                download = true
                workDir = file('build/node')
            }
        ''' )
        writeEmptyPackageJson()

        when:
        def result = buildTask( 'npmInstall' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }

    def 'install packages with npm and postinstall task requiring npm and node'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }
            node {
                npmVersion = "6.1.0"
                download = true
                workDir = file('build/node')
            }
        ''' )
        writePackageJson( """ {
            "name": "example",
            "dependencies": {},
            "versionOutput" : "node --version",
            "postinstall" : "npm run versionOutput"
        }
        """ )
        writeEmptyPackageLockJson()

        when:
        def result = buildTask( 'npmInstall' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }

    def 'install packages with npm in different directory'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                npmVersion = "6.1.0"
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
        writeEmptyPackageLockJson( 'subdirectory/package-lock.json' )

        when:
        def result = build( 'npmInstall' )

        then:
        result.task( ':npmInstall' ).outcome == TaskOutcome.SUCCESS
    }
}
