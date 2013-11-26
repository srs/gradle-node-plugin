package com.moowork.gradle.node.variant

import com.moowork.gradle.node.NodeExtension
import org.gradle.api.Project

class VariantFactory
{
    private final Project project

    private final NodeExtension ext

    VariantFactory( Project project )
    {
        this.project = project
        this.ext = NodeExtension.get( this.project )
    }

    def Variant create( )
    {
        def osName = PlatformHelper.getOsName()
        def osArch = PlatformHelper.getOsArch()

        def variant = new Variant()
        variant.windows = PlatformHelper.isWindows()
        variant.workDir = getWorkDir()
        variant.nodeDir = getNodeDir( osName, osArch )
        variant.nodeBinDir = new File( variant.nodeDir, 'bin' )

        if ( variant.windows )
        {
            variant.tarGzDependency = getTarGzDependency( 'linux', 'x86' )
            variant.exeDependency = getExeDependency()
            variant.npmDir = getNpmDir( 'linux', 'x86' )
            variant.nodeExec = new File( variant.nodeBinDir, 'node.exe' )
        }
        else
        {
            variant.tarGzDependency = getTarGzDependency( osName, osArch )
            variant.npmDir = getNpmDir( osName, osArch )
            variant.nodeExec = new File( variant.nodeBinDir, 'node' )
        }

        variant.npmScriptFile = new File( variant.npmDir, 'npm/bin/npm-cli.js' )
        return variant
    }

    private File getWorkDir( )
    {
        return new File( this.project.getRootDir(), '.gradle/node' )
    }

    private String getTarGzDependency( final String osName, final String osArch )
    {
        return ':node:' + this.ext.nodeVersion + ':' + osName + '-' + osArch + '@tar.gz'
    }

    private String getExeDependency( )
    {
        return ':node:' + this.ext.nodeVersion + '@exe'
    }

    private File getNodeDir( final String osName, final String osArch )
    {
        String dirName = 'node-v' + this.ext.nodeVersion + '-' + osName + '-' + osArch
        return new File( getWorkDir(), dirName )
    }

    private File getNpmDir( final String osName, final String osArch )
    {
        File nodeDir = getNodeDir( osName, osArch )
        return new File( nodeDir, 'lib/node_modules' )
    }
}
