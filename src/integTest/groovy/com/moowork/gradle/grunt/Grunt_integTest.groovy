package com.moowork.gradle.grunt

import com.moowork.gradle.AbstractIntegTest

class Grunt_integTest
    extends AbstractIntegTest
{
    def 'execute simple task'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.grunt'

            node {
                download = true
            }

            grunt {
                colors = true
                bufferOutput = true
            }

            task simple(type: GruntTask) {
                args = ['simple']
            }
        ''' )
        writeFile( 'Gruntfile.js', '''
            module.exports = function(grunt) {
                grunt.registerTask('simple', []);
            }
        ''' )

        when:
        def result = runTasks( 'installGrunt', 'simple' )

        then:
        result.wasExecuted( 'installGrunt' )
        result.wasExecuted( 'simple' )
    }
}
