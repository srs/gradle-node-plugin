package com.moowork.gradle.node

import com.moowork.gradle.node.task.GruntTask
import com.moowork.gradle.node.task.NodeTask
import com.moowork.gradle.node.task.NpmInstallTask
import com.moowork.gradle.node.task.SetupTask
import com.moowork.gradle.node.util.DependencyHelper
import org.gradle.api.Plugin
import org.gradle.api.Project

class NodePlugin implements Plugin<Project>
{
    private Project project

    private NodeExtension config

    @Override
    void apply( final Project project )
    {
        this.project = project
        this.config = this.project.extensions.create( NodeExtension.NAME, NodeExtension )

        addConfigurations();
        addTasks();

        this.project.afterEvaluate {
            addRepositories()
            addDependencies()
        }
    }

    private void addConfigurations( )
    {
        this.project.configurations {
            nodeDist
        }
    }

    private void addRepositories( )
    {
        this.project.repositories {
            ivy {
                url this.config.nodeDistBaseUrl
                layout 'pattern', {
                    artifact 'npm/[artifact]-[revision].[ext]'
                    artifact 'v[revision]/[artifact](-v[revision]-[classifier]).[ext]'
                }
            }
        }
    }

    private void addDependencies( )
    {
        this.project.dependencies {
            nodeDist DependencyHelper.getNodeDependency( this.config )
            nodeDist DependencyHelper.getNpmDependency( this.config )
        }
    }

    private void addTasks( )
    {
        addGlobalTaskType( NodeTask )
        addGlobalTaskType( GruntTask )

        this.project.tasks.create( SetupTask.NAME, SetupTask )
        this.project.tasks.create( NpmInstallTask.NAME, NpmInstallTask )
    }

    private void addGlobalTaskType( Class type )
    {
        this.project.extensions.getExtraProperties().set( type.getSimpleName(), type )
    }
}
