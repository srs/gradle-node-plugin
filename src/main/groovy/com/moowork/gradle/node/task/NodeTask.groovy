package com.moowork.gradle.node.task

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.exec.NodeExecRunner
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecResult

class NodeTask
    extends DefaultTask
{
    protected NodeExecRunner runner

    private File script

    private Iterable<?> args = []

    private ExecResult result

    public NodeTask()
    {
        this.runner = new NodeExecRunner( this.project )
        dependsOn( SetupTask.NAME )
    }

    void setScript( final File value )
    {
        this.script = value
    }

    void setExecutable( final String execName )
    {
        File modulesDir = project.extensions.findByType(NodeExtension).nodeModulesDir
        setScript(new File(modulesDir, 'node_modules/.bin/' + execName))
        dependsOn( NpmInstallTask.NAME )
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

    void setIgnoreExitValue ( final boolean value )
    {
        this.runner.ignoreExitValue = value
    }

    void setExecOverrides ( final Closure closure )
    {
        this.runner.execOverrides = closure
    }

    @Internal
    ExecResult getResult()
    {
        return this.result
    }

    @Internal
    Iterable<?> getArgs()
    {
        return this.args
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
        this.result = this.runner.execute()
    }
}
