package com.moowork.gradle.node.exec

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.variant.Variant
import org.gradle.api.Project
import org.gradle.process.ExecResult

abstract class ExecRunner
{
    protected Project project

    protected NodeExtension ext

    protected Variant variant

    def Map<String, ?> environment = [:]

    def Object workingDir

    def List<?> arguments = []

    def boolean ignoreExitValue

    def Closure execOverrides

    public ExecRunner( final Project project )
    {
        this.project = project
        this.environment << System.getenv()
    }

    protected final ExecResult run( final String exec, final List<?> args )
    {
        def realExec = exec
        def realArgs = args

        return this.project.exec( {
            it.executable = realExec
            it.args = realArgs
            it.environment = this.environment

            if ( this.workingDir != null )
            {
                it.workingDir = this.workingDir
            }

            if ( this.ignoreExitValue != null )
            {
                it.ignoreExitValue = this.ignoreExitValue
            }

            if ( this.execOverrides != null )
            {
                this.execOverrides( it )
            }
        } )
    }

    public final ExecResult execute()
    {
        this.ext = NodeExtension.get( this.project )
        this.variant = this.ext.variant
        return doExecute()
    }

    protected abstract ExecResult doExecute()
}
