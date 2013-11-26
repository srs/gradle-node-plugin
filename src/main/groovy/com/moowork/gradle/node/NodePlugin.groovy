package com.moowork.gradle.node

import com.moowork.gradle.node.task.NodeTask
import com.moowork.gradle.node.task.NpmInstallTask
import com.moowork.gradle.node.task.SetupTask
import com.moowork.gradle.node.variant.VariantFactory
import org.gradle.api.Plugin
import org.gradle.api.Project

class NodePlugin implements Plugin<Project>
{
    private Project project

    private NodeExtension ext

    @Override
    void apply( final Project project )
    {
        this.project = project
        this.ext = this.project.extensions.create( NodeExtension.NAME, NodeExtension )
        this.ext.variant = new VariantFactory( this.project ).create()

        addTasks();
        configureDepedencies();
    }

    private void addTasks( )
    {
        addGlobalTaskType( NodeTask )
        // addGlobalTaskType( GruntTask )

        this.project.tasks.create( SetupTask.NAME, SetupTask )
        this.project.tasks.create( NpmInstallTask.NAME, NpmInstallTask )
    }

    private void addGlobalTaskType( Class type )
    {
        this.project.extensions.getExtraProperties().set( type.getSimpleName(), type )
    }

    private void configureDepedencies( )
    {
        this.project.configurations.create( this.ext.configName )

        this.project.afterEvaluate {
            addRepositories()
            addDependencies()
        }
    }

    private void addRepositories( )
    {
        this.project.repositories {
            ivy {
                url this.ext.nodeDistBaseUrl
                layout 'pattern', {
                    artifact 'v[revision]/[artifact](-v[revision]-[classifier]).[ext]'
                }
            }
        }
    }

    private void addDependencies( )
    {
        this.project.dependencies.add( this.ext.configName, this.ext.variant.tarGzDependency )

        if ( this.ext.variant.windows )
        {
            this.project.dependencies.add( this.ext.configName, this.ext.variant.exeDependency )
        }
    }
}

