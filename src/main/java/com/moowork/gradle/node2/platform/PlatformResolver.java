package com.moowork.gradle.node2.platform;

import java.io.IOException;
import java.util.Properties;
import java.util.function.Supplier;

import com.moowork.gradle.node2.exec.ExecHelper;

public final class PlatformResolver
    implements Supplier<Platform>
{
    private final Properties props;

    public PlatformResolver()
    {
        this( System.getProperties() );
    }

    public PlatformResolver( final Properties props )
    {
        this.props = props;
    }

    private String getProperty( final String name )
    {
        return this.props.getProperty( name, "" ).toLowerCase();
    }

    private String getOsName()
    {
        final String name = getProperty( "os.name" );
        if ( name.contains( "windows" ) )
        {
            return "win";
        }

        if ( name.contains( "mac" ) )
        {
            return "darwin";
        }

        if ( name.contains( "linux" ) )
        {
            return "linux";
        }

        if ( name.contains( "freebsd" ) )
        {
            return "linux";
        }

        if ( name.contains( "sunos" ) )
        {
            return "sunos";
        }

        return name;
    }

    private String getOsArch()
    {
        final String arch = getProperty( "os.arch" );
        if ( arch.contains( "64" ) )
        {
            return "x64";
        }

        //as Java just returns "arm" on all ARM variants, we need a system call to determine the exact arch
        if ( arch.equals( "arm" ) )
        {
            final String uName = findArchUsingUName().trim().toLowerCase();
            if ( uName.equals( "armv8l" ) )
            {
                return "arm64";
            }
            else
            {
                return uName;
            }
        }

        return "x86";
    }

    private String findArchUsingUName()
    {
        try
        {
            return ExecHelper.exec( "uname", "-m" );
        }
        catch ( final IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    @Override
    public Platform get()
    {
        final String osName = getOsName();
        final String osArch = getOsArch();
        final String platform = osName + "-" + osArch;

        return new Platform( platform );
    }
}
