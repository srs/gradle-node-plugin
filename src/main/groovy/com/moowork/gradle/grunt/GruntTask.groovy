package com.moowork.gradle.grunt

import com.moowork.gradle.node.task.NodeTask
import org.gradle.api.GradleException

class GruntTask
    extends NodeTask
{
    private final static String GRUNT_SCRIPT = 'node_modules/grunt-cli/bin/grunt';

    public GruntTask()
    {
        this.group = 'Grunt';
    }

    @Override
    void exec()
    {
        def localGrunt = this.project.file( new File( this.project.node.nodeModulesDir, GRUNT_SCRIPT ) )
        if ( !localGrunt.isFile() )
        {
            throw new GradleException(
                "Grunt-CLI not installed in node_modules, please first run 'gradle ${GruntPlugin.GRUNT_INSTALL_NAME}'" )
        }

        def addArg = { List<?> original, def newArg ->
            def tempArgs = []
            tempArgs.addAll( original )
            tempArgs.add( newArg )
            return tempArgs
        }

        // On multi project scenario, need to specify Gruntfile.js location
        setArgs( addArg( args as List, "--gruntfile ${this.project.grunt.workDir}/Gruntfile.js" ) )

        // If colors are disabled, add --no-color to args
        if ( !this.project.grunt.colors )
        {
            setArgs( addArg( args as List, '--no-color' ) )
        }

        // If output should be buffered (useful when running in parallel)
        // set standardOutput of ExecRunner to a buffer
        def bufferedOutput
        if ( this.project.grunt.bufferOutput )
        {
            bufferedOutput = new ByteArrayOutputStream()
            setExecOverrides( {
                it.standardOutput = bufferedOutput
            } )
        }

        setWorkingDir( this.project.grunt.workDir )
        setScript( localGrunt )

        // If the exec fails, make sure to still print output
        try
        {
            super.exec()
        }
        finally
        {
            // If we were buffering output, print it
            if ( this.project.grunt.bufferOutput )
            {
                println "Output from ${this.project.grunt.workDir}/Gruntfile.js"
                println bufferedOutput.toString()
            }
        }
    }
}
