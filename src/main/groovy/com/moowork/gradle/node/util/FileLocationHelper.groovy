package com.moowork.gradle.node.util

import com.moowork.gradle.node.NodeExtension
import org.gradle.api.Project

class FileLocationHelper
{
    static File getWorkDir( final Project project )
    {
        return new File( project.getRootDir(), '.gradle/node' )
    }

    static File getNodeDir( final Project project )
    {
        NodeExtension config = project.extensions.getByType( NodeExtension )

        String osName = PlatformHelper.osName
        String osArch = PlatformHelper.osArch

        String dirName = 'node-v' + config.nodeVersion + '-' + osName + '-' + osArch
        return new File( getWorkDir( project ), dirName )
    }

    static File getNodeBinDir( final Project project )
    {
        return new File( getNodeDir( project ), 'bin' )
    }

    static File getNodeExec( final Project project )
    {
        final File binDir = getNodeBinDir( project )
        if ( PlatformHelper.isWindows() )
        {
            return new File( binDir, 'node.exe' )
        }
        else
        {
            return new File( binDir, 'node' )
        }
    }

    static File getNodeModulesDir( final Project project )
    {
        return new File( getNodeDir( project ), 'node_modules' )
    }

    static File getNpmScript( final Project project )
    {
        return new File( getNodeModulesDir( project ), 'npm/bin/npm-cli.js' )
    }

    static File getGruntScript( final Project project )
    {
        return new File( project.getProjectDir(), 'node_modules/grunt-cli/bin/grunt' )
    }
}
