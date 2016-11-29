package com.moowork.gradle.node.task

/**
 * Setup a specific version of Yarn to be used by the build.
 **/
class YarnSetupTask
    extends NpmTask
{
    public final static String NAME = 'yarnSetup'

    public YarnSetupTask()
    {
        this.group = 'Node'
        this.description = 'Setup a specific version of Yarn to be used by the build.'
        dependsOn( [NpmSetupTask.NAME] )

        this.project.afterEvaluate {
            getOutputs().dir( this.project.node.yarnWorkDir )
        }
    }

    void configureYarnVersion( String yarnVersion )
    {
        def yarnDir = this.project.node.yarnWorkDir
        if ( !yarnVersion.isEmpty() )
        {
            logger.debug( "Setting yarnVersion to ${yarnVersion}" )
            setArgs( ['install', '--prefix', yarnDir, "yarn@${yarnVersion}"] )
            getInputs().property( 'yarnVersion', yarnVersion )
        }
        else
        {
          setArgs( ['install', '--prefix', yarnDir, "yarn"] )
        }
    }
}
