package com.moowork.gradle.node.yarn

import com.moowork.gradle.node.NodeExtension
import org.gradle.api.tasks.InputFile
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
    }

    @InputFile
    @PathSensitive(PathSensitivity.RELATIVE)
    protected getPackageJsonFile()
    {
        return new File(this.project.extensions.getByType(NodeExtension).nodeModulesDir, 'package.json')
    }

    @InputFile
    @PathSensitive(PathSensitivity.RELATIVE)
    protected getYarnLockFile()
    {
        return new File(this.project.extensions.getByType(NodeExtension).nodeModulesDir, 'yarn.lock')
    }

    @OutputDirectory
    protected getNodeModulesDir()
    {
        return new File(this.project.extensions.getByType(NodeExtension).nodeModulesDir, 'node_modules')
    }
}
