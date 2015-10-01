package com.moowork.gradle.gulp

import com.moowork.gradle.AbstractIntegTest

class GulpRule_integTest
    extends AbstractIntegTest
{
    def 'execute simple task'()
    {
        given:
        writeBuild( '''
            apply plugin: 'com.moowork.gulp'

            node {
                download = true
            }
        ''' )
        writeFile( 'gulpfile.js', '''
            var gulp = require('gulp');

            gulp.task('simple', function(){
            });
        ''' )

        when:
        def result = runTasks( 'installGulp', 'gulp_simple' )

        then:
        result.wasExecuted( 'installGulp' )
        result.wasExecuted( 'gulp_simple' )
    }
}
