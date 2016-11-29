package com.moowork.gradle.grunt

import com.moowork.gradle.AbstractIntegTest
import org.gradle.testkit.runner.TaskOutcome

class GruntRule_integTest
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
        ''' )
        writeFile( 'Gruntfile.js', '''
            module.exports = function(grunt) {
                grunt.registerTask('simple', []);
            }
        ''' )

        when:
        def result = build( 'installGrunt', 'grunt_simple' )

        then:
        result.task( ':installGrunt' ).outcome == TaskOutcome.SUCCESS
        result.task( ':grunt_simple' ).outcome == TaskOutcome.SUCCESS
    }
}
