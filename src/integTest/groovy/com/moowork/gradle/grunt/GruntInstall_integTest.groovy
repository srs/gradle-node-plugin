package com.moowork.gradle.grunt

import com.moowork.gradle.AbstractIntegTest

class GruntInstall_integTest
    extends AbstractIntegTest
{
    def 'grunt install'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.grunt'

            node {
                download = true
            }
        ''' )

        when:
        def result = runTasksSuccessfully( 'installGrunt' )

        then:
        result.wasExecuted( 'installGrunt' )
    }
}
