package com.moowork.gradle.node2.platform;

import org.gradle.api.tasks.Input;

public final class Platform
{
    private final String name;

    public Platform( final String name )
    {
        this.name = name.toLowerCase();
    }

    @Input
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
