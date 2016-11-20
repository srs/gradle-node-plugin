package com.moowork.gradle.node

import com.moowork.gradle.AbstractIntegTest

class YarnRule_integTest
    extends AbstractIntegTest
{
    def 'execute yarn_install rule'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.node'

            node {
                version = "6.9.1"
                yarnVersion = "0.16.1"
                download = true
                workDir = file('build/node')
                yarnWorkDir = file('build/yarn')
            }
        ''' )
        writeEmptyPackageJson()

        when:
        def result = runTasksSuccessfully( 'yarn_install' )

        then:
        result.wasExecuted( 'yarn_install' )
    }

    def 'can execute an yarn module using yarn_run_'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.node'

            node {
                version = "6.9.1"
                yarnVersion = "0.17.5"
                download = true
            }
        ''' )

        copyResources( 'fixtures/npm-missing/package.json', 'package.json' )

        when:
        def result = runTasksSuccessfully( 'yarn_run_parent' )

        then:
        result.success
        fileExists( 'child1.txt' )
        fileExists( 'child2.txt' )
        fileExists( 'parent1.txt' )
        fileExists( 'parent2.txt' )
    }

}
