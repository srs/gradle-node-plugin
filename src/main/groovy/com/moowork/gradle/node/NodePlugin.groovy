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

    private SetupTask setupTask

    @Override
    void apply( final Project project )
    {
        this.project = project
        this.ext = this.project.extensions.create( NodeExtension.NAME, NodeExtension, this.project )

        addGlobalTypes()
        addTasks()
        addNpmRule()

        this.project.afterEvaluate {
            configureSetupTask()
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
        this.project.tasks.create( NpmInstallTask.NAME, NpmInstallTask )
        this.setupTask = this.project.tasks.create( SetupTask.NAME, SetupTask )
    }

    private void addGlobalTaskType( Class type )
    {
        this.project.extensions.extraProperties.set( type.getSimpleName(), type )
    }

    private void addNpmRule()
    {
        // note this rule also makes it possible to specify e.g. "dependsOn npm_install"
        project.getTasks().addRule( 'Pattern: "npm_<command>": Executes an NPM command.' ) { String taskName ->
            if ( taskName.startsWith( "npm_" ) )
            {
                NpmTask npmTask = project.getTasks().create( taskName, NpmTask.class )
                String[] tokens = taskName.split( '_' )
                npmTask.npmCommand = tokens.tail() // all except first
                return npmTask
            }
        }
    }

    private void configureSetupTask()
    {
        this.setupTask.setEnabled( this.ext.download )
    }

    private void configureDepedencies()
    {
        if ( this.ext.download )
        {
            addRepositories()
            addDependencies()
        }
    }

    private void addRepositories()
    {
        this.project.repositories {
            ivy {
                url this.ext.distBaseUrl
                layout 'pattern', {
                    artifact 'v[revision]/[artifact](-v[revision]-[classifier]).[ext]'
                }
            }
        }
    }

    private void addDependencies()
    {
        this.project.configurations.create( NodeExtension.CONFIG_NAME )

        def variant = VariantBuilder.build( this.ext )
        this.project.dependencies.add( NodeExtension.CONFIG_NAME, variant.tarGzDependency )

        if ( variant.exeDependency != null )
        {
            this.project.dependencies.add( NodeExtension.CONFIG_NAME, variant.exeDependency )
        }
    }
}
