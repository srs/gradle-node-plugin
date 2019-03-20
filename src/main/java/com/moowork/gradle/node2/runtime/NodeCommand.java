package com.moowork.gradle.node2.runtime;

import java.io.File;
import java.util.List;
import java.util.Map;

public final class NodeCommand
{
    private File script;

    private File workDir;

    private List<String> options;

    private List<String> args;

    private Map<String, String> environment;

    private Boolean ignoreExitValue;

    public File getScript()
    {
        return this.script;
    }

    public void setScript( final File script )
    {
        this.script = script;
    }

    public File getWorkDir()
    {
        return this.workDir;
    }

    public void setWorkDir( final File workDir )
    {
        this.workDir = workDir;
    }

    public List<String> getOptions()
    {
        return this.options;
    }

    public void setOptions( final List<String> options )
    {
        this.options = options;
    }

    public List<String> getArgs()
    {
        return this.args;
    }

    public void setArgs( final List<String> args )
    {
        this.args = args;
    }

    public Map<String, String> getEnvironment()
    {
        return this.environment;
    }

    public void setEnvironment( final Map<String, String> environment )
    {
        this.environment = environment;
    }

    public Boolean isIgnoreExitValue()
    {
        return this.ignoreExitValue;
    }

    public void setIgnoreExitValue( final Boolean ignoreExitValue )
    {
        this.ignoreExitValue = ignoreExitValue;
    }
}
