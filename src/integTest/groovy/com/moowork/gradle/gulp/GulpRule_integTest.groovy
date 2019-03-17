package com.moowork.gradle.gulp

import com.moowork.gradle.AbstractIntegTest
import org.gradle.testkit.runner.TaskOutcome

class GulpRule_integTest
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
        ''' )
        writeFile( 'gulpfile.js', '''
            var gulp = require('gulp');

            gulp.task('simple', async function(){
            });
        ''' )

        when:
        def result = build( 'installGulp', 'gulp_simple' )

        then:
        result.task( ':installGulp' ).outcome == TaskOutcome.SUCCESS
        result.task( ':gulp_simple' ).outcome == TaskOutcome.SUCCESS
    }
}
