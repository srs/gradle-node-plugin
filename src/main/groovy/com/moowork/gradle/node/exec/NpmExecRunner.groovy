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
            return run( 'npm', this.arguments )
        }

        def runner = new NodeExecRunner( this.project )
        runner.arguments = [this.variant.npmScriptFile] + this.arguments
        runner.environment = this.environment
        runner.workingDir = this.workingDir
        runner.execOverrides = this.execOverrides
        runner.ignoreExitValue = this.ignoreExitValue
        return runner.execute()
    }
}
