package com.moowork.gradle.node.task

/**
 * yarn install that only gets executed if gradle decides so.*/
class YarnInstallTask
    extends YarnTask
{
    public final static String NAME = 'yarn'

    public YarnInstallTask()
    {
        this.group = 'Node'
        this.description = 'Install node packages using Yarn.'
        setYarnCommand( '' )
        dependsOn( [YarnSetupTask.NAME] )

        this.project.afterEvaluate {
            getInputs().file( new File( (File) this.project.node.nodeModulesDir, 'package.json' ) )
            getInputs().file( new File( (File) this.project.node.nodeModulesDir, 'yarn.lock' ) )
            getOutputs().dir( new File( (File) this.project.node.nodeModulesDir, 'node_modules' ) )
        }
    }
}
