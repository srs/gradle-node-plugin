package com.moowork.gradle.node.task

import com.moowork.gradle.node.exec.NodeExecRunner
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class NodeTask
    extends DefaultTask
{
    private NodeExecRunner runner

    private File script

    private Iterable<?> args = []

    public NodeTask()
    {
        this.runner = new NodeExecRunner( this.project )
        dependsOn( SetupTask.NAME )
    }

    void setScript( final File value )
    {
        this.script = value
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
        if ( this.script == null )
        {
            throw new IllegalStateException( 'Required script property is not set.' )
        }

        def execArgs = []
        execArgs.add( this.script.absolutePath )
        execArgs.addAll( this.args as List )

        this.runner.arguments = execArgs
        this.runner.execute()
    }
}
