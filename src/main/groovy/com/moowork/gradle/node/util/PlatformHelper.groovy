package com.moowork.gradle.node.util

class PlatformHelper
{
    static PlatformHelper INSTANCE = new PlatformHelper()

    private final Properties props;

    public PlatformHelper()
    {
        this( System.getProperties() )
    }

    public PlatformHelper( final Properties props )
    {
        this.props = props;
    }

    private String property( final String name )
    {
        def value = this.props.getProperty( name )
        return value != null ? value : System.getProperty( name )
    }

    public String getOsName()
    {
        final String name = property( "os.name" ).toLowerCase()
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

        if ( name.contains( "freebsd" ) )
        {
            return "linux"
        }

        if ( name.contains( "sunos" ) )
        {
            return "sunos"
        }

        throw new IllegalArgumentException( "Unsupported OS: " + name )
    }

    public String getOsArch()
    {
        final String arch = property( "os.arch" ).toLowerCase()
        if ( arch.contains( "64" ) )
        {
            return "x64"
        }
        else
        {
            return "x86"
        }
    }

    public boolean isWindows()
    {
        return getOsName().equals( "windows" )
    }
}
