package com.moowork.gradle.grunt

import com.moowork.gradle.node.npm.NpmTask

class GruntInstallTask
    extends NpmTask
{
    public GruntInstallTask()
    {
        super()

        this.group = 'Grunt'
        this.description = "Runs 'npm install grunt-cli grunt' to install grunt-cli"

        setArgs( ['install', 'grunt-cli', 'grunt'] )

        this.project.afterEvaluate {
            setWorkingDir( this.project.node.nodeModulesDir )
            getOutputs().dir( new File( this.project.node.nodeModulesDir, 'node_modules/grunt' ) )
            getOutputs().dir( new File( this.project.node.nodeModulesDir, 'node_modules/grunt-cli' ) )
        }
    }
}
