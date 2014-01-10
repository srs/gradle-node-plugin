package com.moowork.gradle.node.exec

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.variant.Variant
import com.moowork.gradle.node.variant.VariantBuilder
import org.gradle.api.Project
import org.gradle.process.ExecResult

abstract class ExecRunner
{
    protected Project project

    protected NodeExtension ext

    protected Variant variant

    def Map<String, ?> environment

    def Object workingDir

    def List<?> arguments = []

    public ExecRunner( final Project project )
    {
        this.project = project
    }

    protected final ExecResult run( final String exec, final List<?> args )
    {
        def realExec = exec
        def realArgs = args

        if ( this.variant.windows )
        {
            realExec = 'cmd'
            realArgs = ['/c', exec] + args
        }

        return this.project.exec( {
            it.executable = realExec
            it.args = realArgs

            if ( this.environment != null )
            {
                it.environment = this.environment
            }

            if ( this.workingDir != null )
            {
                it.workingDir = this.workingDir
            }
        } )
    }

    public final ExecResult execute()
    {
        this.ext = NodeExtension.get( this.project )
        this.variant = VariantBuilder.build( this.ext )
        return doExecute()
    }

    protected abstract ExecResult doExecute()
}
