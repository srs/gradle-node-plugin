package com.moowork.gradle.node.npm

import com.moowork.gradle.node.task.SetupTask
import com.moowork.gradle.node.NodeExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecResult

/**
 * npm install that only gets executed if gradle decides so.
 **/
class NpmSetupTask
    extends DefaultTask
{
    public final static String NAME = 'npmSetup'

    private NodeExtension config

    protected Iterable<?> args = []

    private ExecResult result

    public NpmSetupTask()
    {
        dependsOn( SetupTask.NAME )

        this.group = 'Node'
        this.description = 'Setup a specific version of npm to be used by the build.'
        this.enabled = false
    }

    @Input
    public Set<String> getInput()
    {
        def set = new HashSet<>()
        set.add( getConfig().download )
        set.add( getConfig().npmVersion )
        set.add( getConfig().npmWorkDir )
        return set
    }

    @OutputDirectory
    public File getNpmDir()
    {
        return getVariant().npmDir
    }

    @Internal
    ExecResult getResult()
    {
        return this.result
    }

    @Internal
    protected getConfig()
    {
        if ( this.config != null)
        {
            return this.config
        }

        this.config = NodeExtension.get( this.project )
        return this.config
    }

    @Internal
    protected getVariant()
    {
        return getConfig().variant
    }

    @Internal
    void setArgs( final Iterable<?> value )
    {
        this.args = value
    }

    @TaskAction
    void exec() {
        def runner = new NpmExecRunner( this.project )
        runner.arguments.addAll( this.args )

        this.result = runner.execute()
    }

    void configureVersion( String npmVersion )
    {
        if ( !npmVersion.isEmpty() )
        {
            logger.debug( "Setting npmVersion to ${npmVersion}" )
            setArgs( ['install', '--global', '--prefix', getVariant().npmDir, "npm@${npmVersion}"] )
            enabled = true
        }
    }
}
