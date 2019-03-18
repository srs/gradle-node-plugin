package com.moowork.gradle.node2.platform;

public final class Platform
{
    private final String name;

    public Platform( final String name )
    {
        this.name = name.toLowerCase();
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isWindows()
    {
        return this.name.startsWith( "win-" );
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
