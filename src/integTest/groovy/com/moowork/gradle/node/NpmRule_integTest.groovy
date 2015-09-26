package com.moowork.gradle.node

import nebula.test.IntegrationSpec

class NpmRule_integTest
    extends IntegrationSpec
{
    def 'execute npm_install rule'()
    {
        when:
        writeEmptyPackageJson()
        this.buildFile << applyPlugin( NodePlugin )
        this.buildFile << '''
            node {
                version = "0.10.33"
                npmVersion = "2.1.6"
                download = true
                workDir = file('build/node')
            }
        '''.stripIndent()

        def result = runTasksSuccessfully( 'npm_install' )

        then:
        !result.wasUpToDate( 'npm_install' )
    }

    def writeEmptyPackageJson()
    {
        def packageJson = createFile( 'package.json', this.projectDir )
        packageJson << """{
            "name": "example",
            "dependencies": {
            }
        }""".stripIndent()
    }
}
