package com.moowork.gradle.node.npm

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.task.SetupTask
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

    private NpmExecRunner runner

    private NodeExtension config

    protected List<?> args = []

    private ExecResult result

    NpmSetupTask()
    {
        dependsOn( SetupTask.NAME )

        this.group = 'Node'
        this.description = 'Setup a specific version of npm to be used by the build.'
        this.enabled = false

        this.runner = new NpmExecRunner( this.project )
    }

    @Input
    Set<String> getInput()
    {
        def set = new HashSet<>()
        set.add( getConfig().download )
        set.add( getConfig().npmVersion )
        set.add( getConfig().npmWorkDir )
        return set
    }

    @OutputDirectory
    File getNpmDir()
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
        if ( this.config != null )
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

    List<?> getArgs()
    {
        return this.args
    }

    @Internal
    void setArgs( final Iterable<?> value )
    {
        this.args = value.toList()
    }

    void setIgnoreExitValue( final boolean value )
    {
        this.runner.ignoreExitValue = value
    }

    void setExecOverrides( final Closure closure )
    {
        this.runner.execOverrides = closure
    }

    @TaskAction
    void exec()
    {
        this.runner.arguments.addAll( this.args )
        this.result = this.runner.execute()
    }

    void configureVersion( String npmVersion )
    {
        if ( !npmVersion.isEmpty() )
        {
            logger.debug( "Setting npmVersion to ${npmVersion}" )
            setArgs( ['install', '--global', '--no-save'] + proxySettings() + ['--prefix', getVariant().npmDir, "npm@${npmVersion}"] )
            enabled = true
        }
    }

    static List<String> proxySettings() {
        for(String[] proxySettings : [['http', '--proxy'], ['https', '--https-proxy']]) {
            String proxyHost = System.getProperty(proxySettings[0] + '.proxyHost')
            String proxyPort = System.getProperty(proxySettings[0] + '.proxyPort')

            if (proxyHost != null && proxyPort != null) {
                proxyHost = proxyHost.replaceAll('^https?://', '')
                String proxyUser = System.getProperty(proxySettings[0] + '.proxyUser')
                String proxyPassword = System.getProperty(proxySettings[0] + '.proxyPassword')
                if (proxyUser != null && proxyPassword != null) {
                    return ["${proxySettings[1]} ${proxySettings[0]}://$proxyUser:$proxyPassword@$proxyHost:$proxyPort"]
                } else {
                    return ["${proxySettings[1]} ${proxySettings[0]}://$proxyHost:$proxyPort"]
                }
            }
        }
        return []
    }
}
