package com.moowork.gradle.node.task

import com.moowork.gradle.node.util.FileLocationHelper
import org.gradle.api.tasks.InputFile

class GruntTask extends NodeTask
{
    GruntTask( )
    {
        dependsOn( NpmInstallTask.NAME )
    }

    @Override
    void doExecute( )
    {
        this.scriptFile = FileLocationHelper.getGruntScript( this.project )
        super.doExecute()
    }

    @InputFile
    File getGruntFile( )
    {
        return new File( this.project.getProjectDir(), 'Gruntfile.js' )
    }
}
