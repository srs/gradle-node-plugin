package com.moowork.gradle.node

import com.moowork.gradle.AbstractIntegTest

class Setup_integTest
    extends AbstractIntegTest
{
    def 'setup node'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.node'

            node {
                version = "0.10.33"
                download = false
            }
        ''' )

        when:
        def result = runTasksSuccessfully( 'nodeSetup' )

        then:
        result.wasExecuted( 'nodeSetup' )
    }

    def 'setup node (download)'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.node'

            node {
                version = "0.10.33"
                download = true
            }
        ''' )

        when:
        def result = runTasksSuccessfully( 'nodeSetup' )

        then:
        result.wasExecuted( 'nodeSetup' )
    }
}
