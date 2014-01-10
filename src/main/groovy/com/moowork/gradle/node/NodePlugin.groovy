package com.moowork.gradle.node

import com.moowork.gradle.node.task.NodeTask
import com.moowork.gradle.node.task.NpmInstallTask
import com.moowork.gradle.node.task.NpmTask
import com.moowork.gradle.node.task.SetupTask
import com.moowork.gradle.node.variant.VariantBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

class NodePlugin
    implements Plugin<Project>
{
    private Project project

    private NodeExtension ext

    @Override
    void apply( final Project project )
    {
        this.project = project
        this.ext = NodeExtension.create( this.project )

        addGlobalTypes()

        this.project.afterEvaluate {
            addTasks()
            configureDepedencies()
        }
    }

    private void addGlobalTypes()
    {
        addGlobalTaskType( NodeTask )
        addGlobalTaskType( NpmTask )
    }

    private void addTasks()
    {
        this.project.tasks.create( SetupTask.NAME, SetupTask )
        this.project.tasks.create( NpmInstallTask.NAME, NpmInstallTask )
    }

    private void addGlobalTaskType( Class type )
    {
        this.project.extensions.getExtraProperties().set( type.getSimpleName(), type )
    }

    private void configureDepedencies()
    {
        if ( this.ext.installNode )
        {
            addRepositories()
            addDependencies()
        }
    }

    private void addRepositories()
    {
        this.project.repositories {
            ivy {
                url this.ext.nodeDistUrl
                layout 'pattern', {
                    artifact 'v[revision]/[artifact](-v[revision]-[classifier]).[ext]'
                }
            }
        }
    }

    private void addDependencies()
    {
        def variant = VariantBuilder.build( this.ext )

        this.project.configurations.create( this.ext.configName )
        this.project.dependencies.add( this.ext.configName, variant.tarGzDependency )

        if ( variant.exeDependency != null )
        {
            this.project.dependencies.add( this.ext.configName, variant.exeDependency )
        }
    }
}
