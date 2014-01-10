package com.moowork.gradle.node.util

class PlatformHelper
{
    static String getOsName()
    {
        final String name = System.getProperty( "os.name" ).toLowerCase()
        if ( name.contains( "windows" ) )
        {
            return "windows"
        }

        if ( name.contains( "mac" ) )
        {
            return "darwin"
        }

        if ( name.contains( "linux" ) )
        {
            return "linux"
        }

        throw new IllegalArgumentException( "Unsupported OS: " + name )
    }

    static String getOsArch()
    {
        final String arch = System.getProperty( "os.arch" ).toLowerCase()
        if ( arch.contains( "64" ) )
        {
            return "x64"
        }
        else
        {
            return "x86"
        }
    }

    static boolean isWindows()
    {
        return getOsName().equals( "windows" )
    }
}
