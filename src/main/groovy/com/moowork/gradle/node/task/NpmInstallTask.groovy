package com.moowork.gradle.node.task

class NpmInstallTask
    extends NpmTask
{
    public final static String NAME = 'npmInstall'

    public NpmInstallTask()
    {
        this.group = 'Node'
        this.description = 'Install node packages from package.json.'
        setArgs( ['install'] )

        getInputs().file( new File( this.project.getProjectDir(), 'package.json' ) )
        getOutputs().dir( new File( this.project.getProjectDir(), 'node_modules' ) )
    }
}
