package com.moowork.gradle.node2;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Provider;

import com.moowork.gradle.node2.dependency.NodeDependency;
import com.moowork.gradle.node2.dependency.NodeDependencyResolver;
import com.moowork.gradle.node2.runner.NodeRunnerHelper;
import com.moowork.gradle.node2.task.NodeTask;
import com.moowork.gradle.node2.task.SetupTask;

public class NodePlugin
    implements Plugin<Project>
{
    private Project project;

    private NodeExtension ext;

    @Override
    public void apply( final Project project )
    {
        this.project = project;
        this.ext = NodeExtension.create( this.project );

        addSetupTask();
        configureNodeTasks();
    }

    private void addSetupTask()
    {
        this.project.getTasks().create( SetupTask.NAME, SetupTask.class, this::configureSetupTask );
    }

    private void configureSetupTask( final SetupTask task )
    {
        task.setOnlyIf( element -> !this.ext.getUseLocal().get() );
        task.getRepoUrl().set( this.ext.getNodeRepoUrl() );
        task.getNodeDependency().set( resolveNodeDependency() );
    }

    private void configureNodeTasks()
    {
        this.project.getTasks().withType( NodeTask.class, this::configureNodeTask );
    }

    private void configureNodeTask( final NodeTask task )
    {
        task.getExecutable().set( this.project.getLayout().file( resolveNodeExecutable() ) );
        task.getWorkDir().set( this.ext.getWorkDir() );
    }

    private Provider<NodeDependency> resolveNodeDependency()
    {
        return this.project.provider( () -> {
            final NodeDependencyResolver resolver = new NodeDependencyResolver();
            resolver.setCacheDir( this.ext.getCacheDir().get().getAsFile() );
            resolver.setNodeVersion( this.ext.getVersion().get() );
            resolver.setPlatform( this.ext.getPlatform().get() );
            return resolver.resolve();
        } );
    }

    private Provider<File> resolveNodeExecutable()
    {
        return this.project.provider( () -> {
            if ( this.ext.getUseLocal().get() )
            {
                return NodeRunnerHelper.findExecutable();
            }

            return resolveNodeDependency().get().getExecutable();
        } );
    }
}
