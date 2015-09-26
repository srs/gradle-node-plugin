package com.moowork.gradle.node

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
        def result = build( 'npm_install' )

        then:
        result.wasExecuted( 'npm_install' )
    }
}
