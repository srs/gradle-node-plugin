package com.moowork.gradle.node2.runtime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.process.ExecSpec;

import com.moowork.gradle.node2.dependency.NodeDependency;

final class NodeRuntimeImpl
    implements NodeRuntime
{
    private final Project project;

    private final NodeDependency dependency;

    NodeRuntimeImpl( final Project project, final NodeDependency dependency )
    {
        this.project = project;
        this.dependency = dependency;
    }

    @Override
    public File getExecutable()
    {
        return this.dependency.getExecutable();
    }

    @Override
    public void execute( final NodeCommand command )
    {
        this.project.exec( spec -> configure( spec, command ) );
    }

    private void configure( final ExecSpec spec, final NodeCommand command )
    {
        final List<String> args = new ArrayList<>( command.getOptions() );
        args.add( command.getScript().getAbsolutePath() );
        args.addAll( command.getArgs() );

        spec.setExecutable( getExecutable().getAbsoluteFile() );
        spec.setWorkingDir( command.getWorkDir() );
        spec.setEnvironment( command.getEnvironment() );
        spec.setArgs( args );

        if ( command.isIgnoreExitValue() != null )
        {
            spec.setIgnoreExitValue( command.isIgnoreExitValue() );
        }
    }
}
