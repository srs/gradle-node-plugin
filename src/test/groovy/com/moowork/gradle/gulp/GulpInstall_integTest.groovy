package com.moowork.gradle.gulp

import com.moowork.gradle.AbstractIntegTest
import org.gradle.testkit.runner.TaskOutcome

class GulpInstall_integTest
    extends AbstractIntegTest
{
    def 'gulp install'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.gulp'
            }

            node {
                download = true
            }
        ''' )

        when:
        def result = buildTask( 'installGulp' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }
}
