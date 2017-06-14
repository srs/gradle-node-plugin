package com.moowork.gradle.gulp

import com.moowork.gradle.node.task.NodeTask
import org.gradle.api.GradleException

class GulpTask
    extends NodeTask
{
    private final static String GULP_SCRIPT = 'node_modules/gulp/bin/gulp.js';

    public GulpTask()
    {
        this.group = 'Gulp';
    }

    private File findGulp() {
        def currentDir = this.project.node.nodeModulesDir

        while (currentDir != null && currentDir.canRead()) {
            def localGulp = new File( currentDir, GULP_SCRIPT );
            if (localGulp.exists()) {
                return localGulp
            }

            currentDir = currentDir.parentFile
        }

        return null
    }    

    @Override
    void exec()
    {
        def localGulp = findGulp()
        if ( !localGulp )
        {
            throw new GradleException(
                "gulp not installed in node_modules, please first run 'gradle ${GulpPlugin.GULP_INSTALL_NAME}'" )
        }

        // If colors are disabled, add --no-color to args
        if ( !this.project.gulp.colors ) {
            def tempArgs = []
            tempArgs.addAll( args as List )
            tempArgs.add( '--no-color' )
            setArgs( tempArgs )
        }

        // If output should be buffered (useful when running in parallel)
        // set standardOutput of ExecRunner to a buffer
        def bufferedOutput
        if ( this.project.gulp.bufferOutput ) {
            bufferedOutput = new ByteArrayOutputStream()
            setExecOverrides({
                it.standardOutput = bufferedOutput
            })
        }

        setWorkingDir( this.project.gulp.workDir )
        setScript( localGulp )

        // If the exec fails, make sure to still print output
        try {
            super.exec()
        } finally {
            // If we were buffering output, print it
            if ( this.project.gulp.bufferOutput ) {
                println "Output from ${this.project.gulp.workDir}/gulpfile.js"
                println bufferedOutput.toString()
            }
        }
    }
}
