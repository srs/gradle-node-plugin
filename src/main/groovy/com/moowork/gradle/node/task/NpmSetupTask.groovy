package com.moowork.gradle.node.task

/**
 * npm install that only gets executed if gradle decides so.
 **/
class NpmSetupTask
    extends NpmTask
{
    public final static String NAME = 'npmSetup'

    public NpmSetupTask()
    {
        this.group = 'Node'
        this.description = 'Setup a specific version of npm to be used by the build.'
        this.enabled = false

        this.project.afterEvaluate {
            getOutputs().dir( this.project.node.npmWorkDir )
            setWorkingDir( this.project.node.nodeModulesDir )
        }
    }

    void configureNpmVersion( String npmVersion )
    {
        if ( !npmVersion.isEmpty() )
        {
            logger.debug( "Setting npmVersion to ${npmVersion}" )
            setArgs( ['install', '--prefix', this.project.node.npmWorkDir, "npm@${npmVersion}"] )
            setEnabled( true )
            getInputs().property( 'npmVersion', npmVersion )
        }
    }
}
