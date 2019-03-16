package com.moowork.gradle.node.npm
import com.moowork.gradle.node.NodeExtension
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity

/**
 * npm install that only gets executed if gradle decides so.*/
class NpmInstallTask
    extends NpmTask
{
    public final static String NAME = 'npmInstall'

    NpmInstallTask()
    {
        this.group = 'Node'
        this.description = 'Install node packages from package.json.'
        setNpmCommand( 'install' )
        dependsOn( [NpmSetupTask.NAME] )

        this.project.afterEvaluate {
            getInputs().file( new File( (File) this.project.node.nodeModulesDir, 'package.json' ) )
            getInputs().file( new File( (File) this.project.node.nodeModulesDir, 'package-lock.json'))
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
    protected getPackageLockFile()
    {
        def lockFile = new File(this.project.extensions.getByType(NodeExtension).nodeModulesDir, 'package-lock.json')
        return lockFile.exists() ? lockFile : null
    }

    @OutputDirectory
    protected getNodeModulesDir()
    {
        return new File(this.project.extensions.getByType(NodeExtension).nodeModulesDir, 'node_modules')
    }
}
