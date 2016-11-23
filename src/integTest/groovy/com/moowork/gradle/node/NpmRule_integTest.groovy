package com.moowork.gradle.node

import com.moowork.gradle.AbstractIntegTest

class NpmRule_integTest
    extends AbstractIntegTest
{
    def 'execute npm_install rule'()
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
        def result = runTasksSuccessfully( 'npm_install' )

        then:
        result.wasExecuted( 'npm_install' )
    }

    def 'can execute an npm module using npm_run_'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.node'

            node {
                version = "5.9.0"
                npmVersion = "3.8.3"
                download = true
            }
        ''' )

        copyResources( 'fixtures/npm-missing/package.json', 'package.json' )

        when:
        def result = runTasksSuccessfully( 'npm_run_echoTest' )

        then:
        result.success
        fileExists( 'test.txt' )
    }

    def 'succeeds to run npm module using npm_run_ when shrinkwrap contains local npm'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.node'

            node {
                version = "5.9.0"
                npmVersion = "3.8.3"
                download = true
            }
        ''' )

        copyResources( 'fixtures/npm-present/package.json', 'package.json' )
        copyResources( 'fixtures/npm-present/npm-shrinkwrap.json', 'npm-shrinkwrap.json' )

        when:
        def result = runTasksSuccessfully( 'npm_run_parent' )

        then:
        result.success
        fileExists( 'child1.txt' )
        fileExists( 'child2.txt' )
        fileExists( 'parent1.txt' )
        fileExists( 'parent2.txt' )
    }
}
