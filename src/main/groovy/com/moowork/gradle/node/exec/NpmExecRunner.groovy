package com.moowork.gradle.node.exec

import org.gradle.api.Project
import org.gradle.process.ExecResult

final class NpmExecRunner
    extends ExecRunner
{
    public NpmExecRunner( final Project project )
    {
        super( project )
    }

    @Override
    ExecResult execute()
    {
        if ( !this.ext.installNode )
        {
            return run( 'npm', this.arguments )
        }

        def runner = new NodeExecRunner( this.project )
        runner.arguments = [this.variant.npmScriptFile] + this.arguments
        runner.environment = this.environment
        runner.workingDir = this.workingDir
        return runner.execute()
    }
}
