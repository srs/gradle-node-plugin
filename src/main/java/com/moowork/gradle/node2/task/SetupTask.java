package com.moowork.gradle.node2.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.TaskAction;

import com.moowork.gradle.node2.dependency.NodeDependency;
import com.moowork.gradle.node2.util.PropertyHelper;

public class SetupTask
    extends DefaultTask
{
    public static final String NAME = "nodeSetup";

    private final Property<String> repoUrl;

    private final Property<NodeDependency> nodeDependency;

    public SetupTask()
    {
        setGroup( "Node" );
        setDescription( "Download and install a local node version." );

        this.repoUrl = PropertyHelper.property( getProject(), String.class );
        this.nodeDependency = PropertyHelper.property( getProject(), NodeDependency.class );
    }

    @Input
    public Property<String> getRepoUrl()
    {
        return this.repoUrl;
    }

    @Nested
    public Property<NodeDependency> getNodeDependency()
    {
        return this.nodeDependency;
    }

    @TaskAction
    public void exec()
    {
        System.out.println( this.repoUrl.get() );
    }
}
