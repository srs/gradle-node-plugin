package com.moowork.gradle.node2.task;

import java.util.Collections;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecSpec;

import com.moowork.gradle.node2.runtime.NodeCommand;
import com.moowork.gradle.node2.runtime.NodeRuntime;
import com.moowork.gradle.node2.util.PropertyHelper;

public class NodeTask
    extends DefaultTask
{
    private final Property<NodeRuntime> nodeRuntime;

    private final DirectoryProperty workDir;

    private final RegularFileProperty script;

    private final Property<Boolean> ignoreExitValue;

    private final ListProperty<String> args;

    private final ListProperty<String> options;

    private final MapProperty<String, String> environment;

    private Action<ExecSpec> execOverride;

    public NodeTask()
    {
        dependsOn( SetupTask.NAME );
        this.nodeRuntime = PropertyHelper.property( getProject(), NodeRuntime.class );
        this.workDir = PropertyHelper.dirProperty( getProject() );
        this.script = PropertyHelper.fileProperty( getProject() );
        this.ignoreExitValue = PropertyHelper.property( getProject(), Boolean.class );
        this.args = PropertyHelper.listProperty( getProject(), String.class );
        this.options = PropertyHelper.listProperty( getProject(), String.class );

        this.environment = PropertyHelper.mapProperty( getProject(), String.class, String.class );
        this.environment.set( System.getenv() );
    }

    @Nested
    public Property<NodeRuntime> getNodeRuntime()
    {
        return this.nodeRuntime;
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

    private NodeCommand createCommand()
    {
        final NodeCommand command = new NodeCommand();
        command.setScript( this.script.get().getAsFile() );
        command.setWorkDir( this.workDir.get().getAsFile() );
        command.setIgnoreExitValue( this.ignoreExitValue.getOrNull() );
        command.setEnvironment( this.environment.getOrElse( Collections.emptyMap() ) );
        command.setOptions( this.options.getOrElse( Collections.emptyList() ) );
        command.setArgs( this.args.getOrElse( Collections.emptyList() ) );
        return command;
    }

    @TaskAction
    public void exec()
    {
        final NodeCommand command = createCommand();
        final NodeRuntime runtime = this.nodeRuntime.get();
        runtime.execute( command );
    }
}
