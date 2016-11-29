package com.moowork.gradle.gulp

import com.moowork.gradle.node.npm.NpmTask

class GulpInstallTask
    extends NpmTask
{
    public GulpInstallTask()
    {
        super()

        this.group = 'Gulp'
        this.description = "Runs 'npm install gulp' to install gulp"

        setArgs( ['install', 'gulp'] )

        this.project.afterEvaluate {
            setWorkingDir( this.project.node.nodeModulesDir )
            getOutputs().dir( new File( this.project.node.nodeModulesDir, 'node_modules/gulp' ) )
        }
    }
}
