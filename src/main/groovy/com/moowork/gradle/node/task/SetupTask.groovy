package com.moowork.gradle.node.task

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.variant.PlatformHelper
import com.moowork.gradle.node.variant.Variant
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class SetupTask extends DefaultTask
{
    public final static String NAME = 'nodeSetup'

    SetupTask( )
    {
        this.group = 'Node';
        this.description = 'Download and install a local node/npm version.'
    }

    protected NodeExtension getExt( )
    {
        return NodeExtension.get( this.project )
    }

    protected Variant getVariant( )
    {
        return getExt().variant
    }

    @OutputDirectory
    File getOutputDir( )
    {
        return getVariant().nodeDir
    }

    @TaskAction
    void exec( )
    {
        if ( PlatformHelper.isWindows() )
        {
            copyNodeExe()
        }

        unpackNodeTarGz()
    }

    private void copyNodeExe( )
    {
        this.project.copy {
            from getNodeExeFile()
            into getVariant().nodeBinDir
            rename 'node.+\\.exe', 'node.exe'
        }
    }

    private void unpackNodeTarGz( )
    {
        this.project.copy {
            from this.project.tarTree( getNodeTarGzFile() )
            into getVariant().nodeDir.parentFile
        }
    }

    protected File getNodeExeFile( )
    {
        return findSingleFile( '.exe' )
    }

    protected File getNodeTarGzFile( )
    {
        return findSingleFile( '.tar.gz' )
    }

    private File findSingleFile( final String suffix )
    {
        return this.project.configurations.getByName( getExt().configName ).files.find { it.name.endsWith( suffix ) }
    }
}
