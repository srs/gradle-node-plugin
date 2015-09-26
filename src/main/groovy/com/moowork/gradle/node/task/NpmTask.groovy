package com.moowork.gradle.node.task

import com.moowork.gradle.node.exec.NpmExecRunner
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecResult

class NpmTask
    extends DefaultTask
{
    protected NpmExecRunner runner

    private Iterable<?> args = []

    private ExecResult result

    def String[] npmCommand

    public NpmTask()
    {
        this.runner = new NpmExecRunner( this.project )
        dependsOn( SetupTask.NAME )
    }

    void setArgs( final Iterable<?> value )
    {
        this.args = value
    }

    void setNpmCommand( String[] cmd )
    {
        this.npmCommand = cmd
    }

    Iterable<?> getArgs()
    {
        return this.args
    }

    void setEnvironment( final Map<String, ?> value )
    {
        this.runner.environment = value
    }

    void setWorkingDir( final Object value )
    {
        this.runner.workingDir = value
    }

    void setIgnoreExitValue( final boolean value )
    {
        this.runner.ignoreExitValue = value
    }

    void setExecOverrides( final Closure closure )
    {
        this.runner.execOverrides = closure
    }

    ExecResult getResult()
    {
        return this.result
    }

    @TaskAction
    void exec()
    {
        if ( this.npmCommand != null )
        {
            this.runner.arguments.addAll( this.npmCommand )
        }

        this.runner.arguments.addAll( this.args )
        this.result = this.runner.execute()
    }
}
