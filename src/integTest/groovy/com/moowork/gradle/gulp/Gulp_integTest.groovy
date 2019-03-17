package com.moowork.gradle.gulp

import com.moowork.gradle.AbstractIntegTest
import org.gradle.testkit.runner.TaskOutcome

class Gulp_integTest
    extends AbstractIntegTest
{
    def 'execute simple task'()
    {
        given:
        writeBuild( '''
            plugins {
                id 'com.moowork.gulp'
            }

            node {
                download = true
            }

            gulp {
                colors = false
                bufferOutput = true
            }

            task simple(type: GulpTask) {
                args = ['simple']
            }
        ''' )
        writeFile( 'gulpfile.js', '''
            var gulp = require('gulp');

            gulp.task('simple', async function(){
            });
        ''' )

        when:
        def result = build( 'installGulp', 'simple' )

        then:
        result.task( ':installGulp' ).outcome == TaskOutcome.SUCCESS
        result.task( ':simple' ).outcome == TaskOutcome.SUCCESS
    }
}
