package com.moowork.gradle.node.exec

import org.gradle.api.Project
import org.gradle.process.ExecResult

class NpmExecRunner
    extends ExecRunner
{
    public NpmExecRunner( final Project project )
    {
        super( project )
    }

    @Override
    protected ExecResult doExecute()
    {
        if ( !this.ext.download )
        {
            return run( this.ext.npmCommand, this.arguments )
        }

        def String npmScriptFile = this.variant.npmScriptFile
        def File localNpm = project.file( new File( this.ext.nodeModulesDir, 'node_modules/npm/bin/npm-cli.js' ) )

        // Use locally-installed npm if available
        if ( localNpm.exists() )
        {
           npmScriptFile = localNpm.absolutePath
        }

        def runner = new NodeExecRunner( this.project )
        runner.arguments = [npmScriptFile] + this.arguments
        runner.environment = this.environment
        runner.workingDir = this.workingDir
        runner.execOverrides = this.execOverrides
        runner.ignoreExitValue = this.ignoreExitValue
        return runner.execute()
    }
}
