package com.moowork.gradle.node.exec

import org.gradle.api.Project
import org.gradle.process.ExecResult

class NodeExecRunner
    extends ExecRunner
{
    public NodeExecRunner( final Project project )
    {
        super( project )
    }

    @Override
    ExecResult execute()
    {
        println( this.ext.workDir )


        def exec = 'node'
        if ( this.ext.download )
        {
            exec = this.variant.nodeExec
        }

        return run( exec, this.arguments )
    }
}
