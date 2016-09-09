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
        variant.nodeDir = getNodeDir( osName, osArch )

        if ( variant.windows )
        {
            variant.nodeBinDir = variant.nodeDir
            if (hasWindowsZip())
            {
                variant.archiveDependency = getArchiveDependency( osName, osArch, 'zip' )
            }
            else
            {
                variant.archiveDependency = getArchiveDependency( 'linux', 'x86', 'tar.gz' )
                variant.exeDependency = getExeDependency()
            }
            variant.npmDir = new File( variant.nodeBinDir, 'node_modules' )
            variant.nodeExec = new File( variant.nodeBinDir, 'node.exe' ).absolutePath
        }
        else
        {
            variant.nodeBinDir = new File( variant.nodeDir, 'bin' )
            variant.archiveDependency = getArchiveDependency( osName, osArch, 'tar.gz' )
            variant.npmDir = new File( variant.nodeDir, 'lib/node_modules' )
            variant.nodeExec = new File( variant.nodeBinDir, 'node' ).absolutePath
        }

        variant.npmScriptFile = new File( variant.npmDir, 'npm/bin/npm-cli.js' ).absolutePath
        return variant
    }

    private String getArchiveDependency( final String osName, final String osArch, final String type )
    {
        def version = this.ext.version
        return "org.nodejs:node:${version}:${osName}-${osArch}@${type}"
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

    //https://github.com/nodejs/node/pull/5995    
    private boolean hasWindowsZip()
    {
        def version = this.ext.version
        def osArch = platformHelper.getOsArch()
        def tokens = version.tokenize( '.' );
        def majorVersion = tokens[0].toInteger()
        def minorVersion = tokens[1].toInteger()
        if (
               ( majorVersion == 4 && minorVersion >= 5 ) // >= 4.5.0
               || ( majorVersion == 6 && (minorVersion > 2 || (minorVersion == 2 && microVersion >= 1)) ) // >= 6.2.1
               || majorVersion > 6
           )
        {
            return true
        }
        return false
    }

    private File getNodeDir( final String osName, final String osArch )
    {
        def version = this.ext.version
        def dirName = "node-v${version}-${osName}-${osArch}"
        return new File( this.ext.workDir, dirName )
    }
}
