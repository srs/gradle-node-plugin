package com.moowork.gradle.node.util

import com.moowork.gradle.node.NodeExtension

class DependencyHelper
{
    static String getNpmDependency( final NodeExtension config )
    {
        return ':npm:' + config.npmVersion + '@zip'
    }

    static String getNodeDependency( final NodeExtension config )
    {
        String os = PlatformHelper.getOsName()
        String arch = PlatformHelper.getOsArch()

        String dep = ':node:' + config.nodeVersion

        if ( os.equals( 'windows' ) )
        {
            dep += '@exe'
        }
        else
        {
            dep += ':' + os + '-' + arch + '@tar.gz'
        }

        return dep
    }
}
