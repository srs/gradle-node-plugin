package com.moowork.gradle.node.yarn

import com.moowork.gradle.node.npm.NpmSetupTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory

/**
 * Setup a specific version of Yarn to be used by the build.
 **/
class YarnSetupTask
    extends NpmSetupTask
{
    public final static String NAME = 'yarnSetup'

    YarnSetupTask()
    {
        this.group = 'Node'
        this.description = 'Setup a specific version of Yarn to be used by the build.'

        this.enabled = false;
    }

    @Input
    Set<String> getInput()
    {
        def set = new HashSet<>()
        set.add( this.getConfig().download )
        set.add( this.getConfig().yarnVersion )
        return set
    }

    @OutputDirectory
    File getYarnDir()
    {
        return this.getVariant().yarnDir
    }

    void configureVersion( String yarnVersion )
    {
        def pkg = "yarn"

        if ( !yarnVersion.isEmpty() )
        {
            logger.debug( "Setting yarnVersion to ${yarnVersion}" )
            pkg += "@${yarnVersion}"
        }

        this.setArgs( ['install', '--global', '--no-save', '--prefix', this.getVariant().yarnDir, pkg] )
        enabled = true
    }
}
