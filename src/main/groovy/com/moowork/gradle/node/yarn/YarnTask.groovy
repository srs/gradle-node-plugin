package com.moowork.gradle.node.yarn

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecResult

class YarnTask
    extends DefaultTask
{
    protected YarnExecRunner runner

    private Iterable<?> args = []

    private ExecResult result

    private String[] yarnCommand

    public YarnTask()
    {
        this.runner = new YarnExecRunner( this.project )
        dependsOn( YarnSetupTask.NAME )

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

    void setYarnCommand( String[] cmd )
    {
        this.yarnCommand = cmd
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
        if ( this.yarnCommand != null )
        {
            this.runner.arguments.addAll( this.yarnCommand )
        }

        this.runner.arguments.addAll( this.args )
        this.result = this.runner.execute()
    }
}
