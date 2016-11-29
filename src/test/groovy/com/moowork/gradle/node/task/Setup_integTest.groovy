package com.moowork.gradle.node.task

import com.moowork.gradle.AbstractIntegTest
import org.gradle.testkit.runner.TaskOutcome

class Setup_integTest
    extends AbstractIntegTest
{
    private final static OS_NAME = System.getProperty( 'os.name' )

    def cleanup()
    {
        System.setProperty( 'os.name', OS_NAME )
    }

    def 'setup node'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                version = "0.10.33"
                download = false
            }
        ''' )

        when:
        def result = buildTask( 'nodeSetup' )

        then:
        result.outcome == TaskOutcome.SKIPPED
    }

    def 'setup node (download)'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                version = "0.10.33"
                download = true
            }
        ''' )

        when:
        def result = buildTask( 'nodeSetup' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }

    def 'setup node (windows)'()
    {
        System.setProperty( 'os.name', 'Windows' )

        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                version = "0.10.33"
                download = false
            }
        ''' )

        when:
        def result = buildTask( 'nodeSetup' )

        then:
        result.outcome == TaskOutcome.SKIPPED
    }

    def 'setup node (windows download)'()
    {
        System.setProperty( 'os.name', 'Windows' )

        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                version = "4.5.0"
                download = true
            }
        ''' )

        when:
        def result = buildTask( 'nodeSetup' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }

    def 'setup node (windows download separate exe)'()
    {
        System.setProperty( 'os.name', 'Windows' )

        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                version = "0.10.33"
                download = true
            }
        ''' )

        when:
        def result = buildTask( 'nodeSetup' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }
}
