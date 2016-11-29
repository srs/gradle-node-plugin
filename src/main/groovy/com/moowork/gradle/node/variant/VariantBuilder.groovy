package com.moowork.gradle.node.variant

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.util.PlatformHelper

class VariantBuilder
{
    private final NodeExtension ext
    private PlatformHelper platformHelper

    public VariantBuilder( final NodeExtension ext )
    {
        this.ext = ext
        this.platformHelper = PlatformHelper.INSTANCE
    }

    public VariantBuilder( final NodeExtension ext , PlatformHelper platformHelper)
    {
        this(ext)
        this.platformHelper = platformHelper
    }

    public Variant build()
    {
        def osName = platformHelper.getOsName()
        def osArch = platformHelper.getOsArch()

        def variant = new Variant()
        variant.windows = platformHelper.isWindows()
        variant.packaged = platformHelper.isPackaged()
        variant.nodeDir = getNodeDir( osName, osArch )
        variant.nodeBinDir = new File( variant.nodeDir, 'bin' )

        if ( variant.windows )
        {
            variant.tarGzDependency = getTarGzDependency( 'linux', 'x86' )
            variant.exeDependency = getExeDependency()
            variant.npmDir = getNpmDir( 'linux', 'x86' )
            variant.nodeExec = new File( variant.nodeBinDir, 'node.exe' ).absolutePath
        }
        else
        {
            if ( variant.isPackaged() ) {
                variant.tarGzDependency = getTarGzDependency(osName, osArch)
            }
            variant.npmDir = getNpmDir( osName, osArch )
            variant.nodeExec = new File( variant.nodeBinDir, 'node' ).absolutePath
        }

        variant.npmScriptFile = new File( variant.npmDir, 'npm/bin/npm-cli.js' ).absolutePath
        return variant
    }

    private String getTarGzDependency( final String osName, final String osArch )
    {
        def version = this.ext.version
        return "org.nodejs:node:${version}:${osName}-${osArch}@tar.gz"
    }

    private String getExeDependency()
    {
        def version = this.ext.version
        def osArch = platformHelper.getOsArch()
        def majorVersion = version.tokenize( '.' )[0].toInteger()
        if ( majorVersion > 3 )
        {
            if ( osArch.equals( "x86" ) )
            {
                return "org.nodejs:win-x86/node:${version}@exe"
            }
            else
            {
                return "org.nodejs:win-x64/node:${version}@exe"
            }
        }
        else
        {
            if ( osArch.equals( "x86" ) )
            {
                return "org.nodejs:node:${version}@exe"
            }
            else
            {
                return "org.nodejs:x64/node:${version}@exe"
            }
        }
    }

    private File getNodeDir( final String osName, final String osArch )
    {
        def version = this.ext.version
        def dirName = "node-v${version}-${osName}-${osArch}"
        return new File( this.ext.workDir, dirName )
    }

    private File getNpmDir( final String osName, final String osArch )
    {
        def nodeDir = getNodeDir( osName, osArch )
        return new File( nodeDir, 'lib/node_modules' )
    }
}
