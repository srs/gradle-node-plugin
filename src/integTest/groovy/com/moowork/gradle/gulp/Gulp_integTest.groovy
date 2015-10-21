package com.moowork.gradle.gulp

import com.moowork.gradle.AbstractIntegTest

class Gulp_integTest
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

            gulp.task('simple', function(){
            });
        ''' )

        when:
        def result = runTasks( 'installGulp', 'simple' )

        then:
        result.wasExecuted( 'installGulp' )
        result.wasExecuted( 'simple' )
    }
}
