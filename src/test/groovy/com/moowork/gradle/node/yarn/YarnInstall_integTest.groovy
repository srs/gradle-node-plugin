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
                id 'com.github.node-gradle.node'
            }

            node {
                version = "10.14.0"
                yarnVersion = "1.12.3"
                download = true
                workDir = file('build/node')
                yarnWorkDir = file('build/yarn')
            }
        ''' )
        writeEmptyPackageJson()

        when:
        def result = buildTask( 'yarn' )

        then:
        result.outcome == TaskOutcome.SUCCESS

        when:
        result = buildTask( 'yarn' )

        then:
        result.outcome == TaskOutcome.SUCCESS

        when:
        result = buildTask( 'yarn' )

        then:
        result.outcome == TaskOutcome.UP_TO_DATE
    }

    def 'install packages with yarn and and postinstall task requiring node and yarn'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.github.node-gradle.node'
            }
            node {
                version = "10.14.0"
                yarnVersion = "1.12.3"
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

        when:
        def result = buildTask( 'yarn' )

        then:
        result.outcome == TaskOutcome.SUCCESS

        when:
        result = buildTask( 'yarn' )

        then:
        result.outcome == TaskOutcome.SUCCESS

        when:
        result = buildTask( 'yarn' )

        then:
        result.outcome == TaskOutcome.UP_TO_DATE
    }

    def 'install packages with yarn in different directory'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.github.node-gradle.node'
            }

            node {
                version = "10.14.0"
                yarnVersion = "1.12.3"
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
        def result = buildTask( 'yarn' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }
}
