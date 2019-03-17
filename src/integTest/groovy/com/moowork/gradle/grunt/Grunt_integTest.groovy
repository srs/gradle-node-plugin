package com.moowork.gradle.grunt

import com.moowork.gradle.AbstractIntegTest
import org.gradle.testkit.runner.TaskOutcome

class Grunt_integTest
    extends AbstractIntegTest
{
    def 'execute simple task'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.grunt'
            }

            node {
                download = true
            }

            grunt {
                colors = false
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
        def result = build( 'installGrunt', 'simple' )

        then:
        result.task( ':installGrunt' ).outcome == TaskOutcome.SUCCESS
        result.task( ':simple' ).outcome == TaskOutcome.SUCCESS
    }
}
