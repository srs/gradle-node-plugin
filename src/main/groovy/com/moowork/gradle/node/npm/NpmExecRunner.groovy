package com.moowork.gradle.node.npm

import com.moowork.gradle.node.exec.ExecRunner
import com.moowork.gradle.node.exec.NodeExecRunner
import org.gradle.api.InvalidUserDataException
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
        if ( this.ext.npmCommand == 'npm' && this.ext.variant.windows ) {
            this.ext.npmCommand = 'npm.cmd'
        }

        if ( !this.ext.download )
        {
            return run( this.ext.npmCommand, this.arguments )
        }

        def String npmScriptFile = this.variant.npmScriptFile
        def File localNpm = project.file( new File( this.ext.nodeModulesDir, 'node_modules/npm/bin/npm-cli.js' ) )
        def File workNpm = project.file( new File( this.ext.npmWorkDir, 'node_modules/npm/bin/npm-cli.js' ) )

        // Use npm specified by user if available
        if ( workNpm.exists() )
        {
            npmScriptFile = workNpm.absolutePath
        }
        // Use locally-installed npm if available
        else if ( localNpm.exists() ) {
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
