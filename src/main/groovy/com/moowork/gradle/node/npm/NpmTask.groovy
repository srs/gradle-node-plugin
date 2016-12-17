package com.moowork.gradle.node.npm

import com.moowork.gradle.node.task.SetupTask
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecResult

class NpmTask
    extends DefaultTask
{
    protected NpmExecRunner runner

    private Iterable<?> args = []

    private ExecResult result

    private String[] npmCommand

    public NpmTask()
    {
        this.runner = new NpmExecRunner( this.project )
        dependsOn( SetupTask.NAME )

        this.project.afterEvaluate {
            if ( !this.runner.workingDir )
            {
                def workingDir = this.project.node.nodeModulesDir
                setWorkingDir( workingDir )
            }

            if ( !this.runner.workingDir.exists() )
            {
                this.runner.workingDir.mkdirs();
            }
        }
    }

    void setArgs( final Iterable<?> value )
    {
        this.args = value
    }

    void setNpmCommand( String[] cmd )
    {
        this.npmCommand = cmd
    }

    @Internal
    Iterable<?> getArgs()
    {
        return this.args
    }

    void setEnvironment( final Map<String, ?> value )
    {
        this.runner.environment << value
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

    @Internal
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
