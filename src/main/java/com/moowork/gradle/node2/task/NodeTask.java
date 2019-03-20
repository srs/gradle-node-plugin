package com.moowork.gradle.node2.task;

import java.util.Collections;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

import com.moowork.gradle.node2.runner.NodeRunner;
import com.moowork.gradle.node2.util.PropertyHelper;

public class NodeTask
    extends DefaultTask
{
    private final RegularFileProperty executable;

    private final DirectoryProperty workDir;

    private final RegularFileProperty script;

    private final Property<Boolean> ignoreExitValue;

    private final ListProperty<String> args;

    private final ListProperty<String> options;

    private final MapProperty<String, String> environment;

    public NodeTask()
    {
        dependsOn( SetupTask.NAME );
        this.executable = PropertyHelper.fileProperty( getProject() );
        this.workDir = PropertyHelper.dirProperty( getProject() );
        this.script = PropertyHelper.fileProperty( getProject() );
        this.ignoreExitValue = PropertyHelper.property( getProject(), Boolean.class );
        this.args = PropertyHelper.listProperty( getProject(), String.class );
        this.options = PropertyHelper.listProperty( getProject(), String.class );

        this.environment = PropertyHelper.mapProperty( getProject(), String.class, String.class );
        this.environment.set( System.getenv() );
    }

    @Input
    public RegularFileProperty getExecutable()
    {
        return this.executable;
    }

    @Input
    public DirectoryProperty getWorkDir()
    {
        return this.workDir;
    }

    @Input
    public RegularFileProperty getScript()
    {
        return this.script;
    }

    @Input
    @Optional
    public Property<Boolean> getIgnoreExitValue()
    {
        return this.ignoreExitValue;
    }

    @Input
    @Optional
    public ListProperty<String> getArgs()
    {
        return this.args;
    }

    @Input
    @Optional
    public ListProperty<String> getOptions()
    {
        return this.options;
    }

    @Input
    @Optional
    public MapProperty<String, String> getEnvironment()
    {
        return this.environment;
    }

    @TaskAction
    public void exec()
    {
        final NodeRunner runner = new NodeRunner();
        runner.setProject( getProject() );
        runner.setExecutable( this.executable.getAsFile().get() );
        runner.setScript( this.script.get().getAsFile() );
        runner.setWorkDir( this.workDir.get().getAsFile() );
        runner.setIgnoreExitValue( this.ignoreExitValue.getOrNull() );
        runner.setEnvironment( this.environment.getOrElse( Collections.emptyMap() ) );
        runner.setOptions( this.options.getOrElse( Collections.emptyList() ) );
        runner.setArgs( this.args.getOrElse( Collections.emptyList() ) );

        runner.execute();
    }
}
