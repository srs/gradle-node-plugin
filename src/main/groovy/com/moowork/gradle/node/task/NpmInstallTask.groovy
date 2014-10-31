package com.moowork.gradle.node.task

/**
 * npm install that only gets executed if gradle decides so.
 */
class NpmInstallTask
    extends NpmTask
{
    public final static String NAME = 'npmInstall'

    public NpmInstallTask()
    {
        this.group = 'Node'
        this.description = 'Install node packages from package.json.'
        setNpmCommand('install')
        dependsOn( [NpmSetupTask.NAME] )

        getInputs().file( new File( this.project.getProjectDir(), 'package.json' ) )
        getOutputs().dir( new File( this.project.getProjectDir(), 'node_modules' ) )
    }
}
