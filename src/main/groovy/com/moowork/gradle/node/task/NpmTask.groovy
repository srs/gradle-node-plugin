package com.moowork.gradle.node.task

import com.moowork.gradle.node.exec.NpmExecRunner
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class NpmTask
    extends DefaultTask
{
    private NpmExecRunner runner

    private Iterable<?> args = []

    public NpmTask()
    {
        this.runner = new NpmExecRunner( this.project )
        dependsOn( SetupTask.NAME )
    }

    void setArgs( final Iterable<?> value )
    {
        this.args = value
    }

    void setEnvironment( final Map<String, ?> value )
    {
        this.runner.environment = value
    }

    void setWorkingDir( final Object value )
    {
        this.runner.workingDir = value
    }

    @TaskAction
    void exec()
    {
        this.runner.arguments = this.args as List
        this.runner.execute()
    }
}
