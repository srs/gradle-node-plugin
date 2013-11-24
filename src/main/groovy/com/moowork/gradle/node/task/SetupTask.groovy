package com.moowork.gradle.node.task

import com.moowork.gradle.node.util.FileLocationHelper
import com.moowork.gradle.node.util.PlatformHelper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class SetupTask extends DefaultTask
{
    final static String NAME = 'nodeSetup'

    SetupTask( )
    {
        this.description = 'Download and install a local node/npm version.'
    }

    @TaskAction
    void exec( )
    {
        if ( PlatformHelper.isWindows() )
        {
            copyNode()
        }
        else
        {
            unpackNode()
        }

        unpackNpm()
    }

    private void copyNode( )
    {
        this.project.copy {
            from getNodeDist()
            into FileLocationHelper.getNodeDir( this.project )
            rename 'node.+\\.exe', 'node.exe'
        }
    }

    private void unpackNode( )
    {
        this.project.copy {
            from this.project.tarTree( getNodeDist() ).matching { include '*/bin/node' }
            into getDestDir().parentFile
        }
    }

    private void unpackNpm( )
    {
        this.project.copy {
            from this.project.zipTree( getNpmDist() )
            into getDestDir()
        }
    }

    @InputFile
    File getNodeDist( )
    {
        return findSingleFile( 'node' )
    }

    @InputFile
    File getNpmDist( )
    {
        return findSingleFile( 'npm' )
    }

    @OutputDirectory
    File getDestDir( )
    {
        return FileLocationHelper.getNodeDir( this.project )
    }

    private File findSingleFile( final String name )
    {
        return this.project.configurations.getByName( 'nodeDist' ).fileCollection { it.name == name }.singleFile
    }
}
