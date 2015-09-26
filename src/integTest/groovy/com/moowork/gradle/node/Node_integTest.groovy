package com.moowork.gradle.node

import nebula.test.IntegrationSpec

class Node_integTest
    extends IntegrationSpec
{
    def 'exec simple node program'()
    {
        when:
        writeEmptyPackageJson()
        writeSimpleJs()
        
        this.buildFile << applyPlugin( NodePlugin )
        this.buildFile << '''
            node {
                version = "0.10.33"
                npmVersion = "2.1.6"
                download = true
                workDir = file('build/node')
            }

            task simple(type: NodeTask) {
                script = file('simple.js')
                args = []
            }

        '''.stripIndent()

        def result = runTasksSuccessfully( 'simple' )

        then:
        !result.wasUpToDate( 'simple' )
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

    def writeSimpleJs()
    {
        def js = createFile( 'simple.js', this.projectDir )
        js << """
            console.log("Hello World");
        """.stripIndent()
    }
}
