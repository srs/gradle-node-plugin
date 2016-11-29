package com.moowork.gradle.node.itest

import org.gradle.testkit.runner.TaskOutcome

class NpmRule_integTest
    extends AbstractIntegTest
{
    def 'execute npm_install rule'()
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
        def result = build( 'npm_install' )

        then:
        result.task( ':npm_install' ).outcome == TaskOutcome.SUCCESS
    }

    def 'can execute an npm module using npm_run_'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                version = "5.9.0"
                npmVersion = "3.8.3"
                download = true
            }
        ''' )

        copyResources( 'fixtures/npm-missing/package.json', 'package.json' )

        when:
        def result = buildTask( 'npm_run_parent' )

        then:
        result.outcome == TaskOutcome.SUCCESS
        fileExists( 'child1.txt' )
        fileExists( 'child2.txt' )
        fileExists( 'parent1.txt' )
        fileExists( 'parent2.txt' )
    }

    def 'fails to run npm module using npm_run_ when shrinkwrap removes local npm'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                version = "5.9.0"
                npmVersion = "3.8.3"
                download = true
            }
        ''' )

        copyResources( 'fixtures/npm-missing/package.json', 'package.json' )
        copyResources( 'fixtures/npm-missing/npm-shrinkwrap.json', 'npm-shrinkwrap.json' )

        when:
        def result = buildAndFail( 'npm_run_parent' )
        def task = result.task( ':npm_run_parent' )

        then:
        task.outcome == TaskOutcome.FAILED
        result.output.contains( 'Could not run npm command - local npm not found but requested in gradle node' )
        result.output.contains( '3.8.3' )
    }

    def 'succeeds to run npm module using npm_run_ when shrinkwrap contains local npm'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                version = "5.9.0"
                npmVersion = "3.8.3"
                download = true
            }
        ''' )

        copyResources( 'fixtures/npm-present/package.json', 'package.json' )
        copyResources( 'fixtures/npm-present/npm-shrinkwrap.json', 'npm-shrinkwrap.json' )

        when:
        def result = buildTask( 'npm_run_parent' )

        then:
        result.outcome == TaskOutcome.SUCCESS
        fileExists( 'child1.txt' )
        fileExists( 'child2.txt' )
        fileExists( 'parent1.txt' )
        fileExists( 'parent2.txt' )
    }
}
