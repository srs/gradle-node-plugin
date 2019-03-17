package com.moowork.gradle.node.task

import com.moowork.gradle.AbstractIntegTest
import org.gradle.testkit.runner.TaskOutcome

class Node_integTest
    extends AbstractIntegTest
{
    def 'exec simple node program'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                download = true
                workDir = file('build/node')
            }

            task simple(type: NodeTask) {
                script = file('simple.js')
                args = []
            }
        ''' )
        writeEmptyPackageJson()
        writeFile( 'simple.js', """
            console.log("Hello World");
        """ )

        when:
        def result = buildTask( 'simple' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }

    def 'check environment settings'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.node'
            }

            node {
                download = true
                workDir = file('build/node')
            }

            task simple(type: NodeTask) {
                script = file('simple.js')
                args = []
                environment = ['MYENV': 'value']
            }
        ''' )
        writeEmptyPackageJson()
        writeFile( 'simple.js', """
            if (process.env.MYENV == 'value') {
                console.log("Hello MYENV=" + process.env.MYENV);
            } else {
                throw "Environment MYENV should be visible";
            }
        """ )

        when:
        def result = buildTask( 'simple' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }
}
