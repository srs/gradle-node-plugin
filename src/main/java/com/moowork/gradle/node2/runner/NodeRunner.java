package com.moowork.gradle.node2.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.process.ExecSpec;

public final class NodeRunner
{
    private Project project;

    private File executable;

    private File script;

    private File workDir;

    private List<String> options;

    private List<String> args;

    private Map<String, String> environment;

    private Boolean ignoreExitValue;

    public void setProject( final Project project )
    {
        this.project = project;
    }

    public void setExecutable( final File executable )
    {
        this.executable = executable;
    }

    public void setScript( final File script )
    {
        this.script = script;
    }

    public void setWorkDir( final File workDir )
    {
        this.workDir = workDir;
    }

    public void setOptions( final List<String> options )
    {
        this.options = options;
    }

    public void setArgs( final List<String> args )
    {
        this.args = args;
    }

    public void setEnvironment( final Map<String, String> environment )
    {
        this.environment = environment;
    }

    public void setIgnoreExitValue( final Boolean ignoreExitValue )
    {
        this.ignoreExitValue = ignoreExitValue;
    }

    public void execute()
    {
        this.project.exec( this::configure );
    }

    private void configure( final ExecSpec spec )
    {
        final List<String> allArgs = new ArrayList<>( this.options );
        allArgs.add( this.script.getAbsolutePath() );
        allArgs.addAll( this.args );

        spec.setExecutable( this.executable.getAbsoluteFile() );
        spec.setWorkingDir( this.workDir );
        spec.setEnvironment( this.environment );
        spec.setArgs( allArgs );

        if ( ignoreExitValue != null )
        {
            spec.setIgnoreExitValue( ignoreExitValue );
        }
    }
}
