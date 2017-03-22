package com.moowork.gradle.node.yarn

import com.moowork.gradle.node.NodeExtension
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity

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

    @InputFile
    @Optional
    @PathSensitive(PathSensitivity.RELATIVE)
    protected getPackageJsonFile()
    {
        def packageJsonFile = new File(this.project.extensions.getByType(NodeExtension).nodeModulesDir, 'package.json')
        return packageJsonFile.exists() ? packageJsonFile : null
    }

    @InputFile
    @Optional
    @PathSensitive(PathSensitivity.RELATIVE)
    protected getYarnLockFile()
    {
        def lockFile = new File(this.project.extensions.getByType(NodeExtension).nodeModulesDir, 'yarn.lock')
        return lockFile.exists() ? lockFile : null
    }

    @OutputDirectory
    protected getNodeModulesDir()
    {
        return new File(this.project.extensions.getByType(NodeExtension).nodeModulesDir, 'node_modules')
    }
}
