package com.moowork.gradle.grunt

import com.moowork.gradle.AbstractIntegTest
import org.gradle.testkit.runner.TaskOutcome

class GruntInstall_integTest
    extends AbstractIntegTest
{
    def 'grunt install'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.grunt'
            }

            node {
                download = true
            }
        ''' )

        when:
        def result = buildTask( 'installGrunt' )

        then:
        result.outcome == TaskOutcome.SUCCESS
    }
}
