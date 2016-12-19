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
        variant.npmDir = ext.npmVersion ? getNpmDir() : variant.nodeDir
        variant.yarnDir = getYarnDir()

        variant.nodeBinDir = variant.nodeDir
        variant.npmBinDir = variant.npmDir
        variant.yarnBinDir = variant.yarnDir

        variant.nodeExec = "node"
        variant.npmExec = this.ext.npmCommand
        variant.yarnExec = this.ext.yarnCommand

        if ( variant.windows )
        {
            if (variant.npmExec == 'npm') {
                variant.npmExec = 'npm.cmd'
            }
            if (variant.yarnExec == 'yarn') {
                variant.yarnExec = 'yarn.cmd'
            }

            if (hasWindowsZip())
            {
                variant.archiveDependency = getArchiveDependency( osName, osArch, 'zip' )
            }
            else
            {
                variant.archiveDependency = getArchiveDependency( 'linux', 'x86', 'tar.gz' )
                variant.exeDependency = getExeDependency()
            }
            variant.npmScriptFile = new File( variant.nodeDir , 'node_modules/npm/bin/npm-cli.js')
        }
        else
        {
            variant.nodeBinDir = new File( variant.nodeBinDir, 'bin' )
            variant.npmBinDir = new File( variant.npmBinDir, 'bin' )
            variant.yarnBinDir = new File( variant.yarnBinDir, 'bin' )
            variant.archiveDependency = getArchiveDependency( osName, osArch, 'tar.gz' )
            variant.npmScriptFile = new File( variant.nodeDir , 'lib/node_modules/npm/bin/npm-cli.js')
        }

        if (this.ext.download)
        {
            if (variant.nodeExec == "node" && variant.windows) {
                variant.nodeExec = "node.exe"
            }

            variant.nodeExec = new File( variant.nodeBinDir, variant.nodeExec ).absolutePath
            variant.npmExec = new File( variant.npmBinDir, variant.npmExec ).absolutePath
            variant.yarnExec = new File( variant.yarnBinDir, variant.yarnExec ).absolutePath
        }

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
        def microVersion = tokens[2].toInteger()
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

    private File getNpmDir()
    {
        def version = this.ext.npmVersion
        return new File(this.ext.npmWorkDir, "npm-v${version}")
    }

    private File getYarnDir()
    {
        def dirname = "yarn"
        if (this.ext.yarnVersion)
        {
            dirname += "-v${this.ext.yarnVersion}"
        }
        else
        {
            dirname += "-latest"
        }
        return new File( this.ext.yarnWorkDir, dirname )
    }
}
