package com.moowork.gradle.node.yarn

import com.moowork.gradle.AbstractIntegTest
import org.gradle.testkit.runner.TaskOutcome

class YarnInstall_integTest
    extends AbstractIntegTest
{
    def 'install packages with yarn'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                yarnVersion = "1.15.2"
                download = true
                workDir = file('build/node')
                yarnWorkDir = file('build/yarn')
            }
        ''' )
        writeEmptyPackageJson()
        writeFile( "yarn.lock", "" )

        when:
        def result = buildTask( 'yarn' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }

    def 'install packages with yarn and and postinstall task requiring node and yarn'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }
            node {
                yarnVersion = "1.15.2"
                download = true
                workDir = file('build/node')
                yarnWorkDir = file('build/yarn')
            }
        ''' )
        writePackageJson(""" {
            "name": "example",
            "dependencies": {},
            "versionOutput" : "node --version",
            "postinstall" : "yarn run versionOutput"
        }
        """)
        writeFile( "yarn.lock", "" )

        when:
        def result = buildTask( 'yarn' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }

    def 'install packages with yarn in different directory'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                yarnVersion = "1.15.2"
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
        writeFile( "subdirectory/yarn.lock", "" )

        when:
        def result = buildTask( 'yarn' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }
}
