package com.moowork.gradle.node

import com.moowork.gradle.node.task.*
import com.moowork.gradle.node.variant.VariantBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

class NodePlugin
    implements Plugin<Project>
{
    private Project project

    private NodeExtension config

    private SetupTask setupTask

    private NpmSetupTask npmSetupTask

    @Override
    void apply( final Project project )
    {
        this.project = project
        this.config = this.project.extensions.create( NodeExtension.NAME, NodeExtension, this.project )

        addGlobalTypes()
        addTasks()
        addNpmRule()

        this.project.afterEvaluate {
            this.config.variant = new VariantBuilder( this.config ).build()
            configureSetupTask()
            configureNpmSetupTask()
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
        this.npmSetupTask = this.project.tasks.create( NpmSetupTask.NAME, NpmSetupTask )
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
                String[] tokens = taskName.split( '_' ).tail() // all except first
                npmTask.npmCommand = tokens

                if (tokens.head().equalsIgnoreCase("run")) {
                    npmTask.dependsOn(NpmInstallTask.NAME)
                }

                return npmTask
            }
        }
    }

    private void configureSetupTask()
    {
        if ( this.config.variant.packaged ) {
            this.setupTask.setEnabled(this.config.download)
        }
    }

    private void configureNpmSetupTask()
    {
        this.npmSetupTask.configureNpmVersion( this.config.npmVersion )
    }
}
