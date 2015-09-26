package com.moowork.gradle.node.task

/**
 * npm install that only gets executed if gradle decides so.*/
class NpmInstallTask
    extends NpmTask
{
    public final static String NAME = 'npmInstall'

    public NpmInstallTask()
    {
        this.group = 'Node'
        this.description = 'Install node packages from package.json.'
        setNpmCommand( 'install' )
        dependsOn( [NpmSetupTask.NAME] )

        this.project.afterEvaluate {
            getInputs().file( new File( (File) this.project.node.nodeModulesDir, 'package.json' ) )
            getOutputs().dir( new File( (File) this.project.node.nodeModulesDir, 'node_modules' ) )
            setWorkingDir( this.project.node.nodeModulesDir )
        }
    }
}
