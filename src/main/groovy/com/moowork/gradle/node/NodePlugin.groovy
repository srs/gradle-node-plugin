package com.moowork.gradle.node

import com.moowork.gradle.node.npm.NpmInstallTask
import com.moowork.gradle.node.npm.NpmSetupTask
import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask
import com.moowork.gradle.node.task.SetupTask
import com.moowork.gradle.node.variant.VariantBuilder
import com.moowork.gradle.node.yarn.YarnInstallTask
import com.moowork.gradle.node.yarn.YarnSetupTask
import com.moowork.gradle.node.yarn.YarnTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class NodePlugin
    implements Plugin<Project>
{
    private Project project

    private NodeExtension config

    private SetupTask setupTask

    private NpmSetupTask npmSetupTask

    private YarnSetupTask yarnSetupTask

    @Override
    void apply( final Project project )
    {
        this.project = project
        this.config = NodeExtension.create( this.project )

        addGlobalTypes()
        addTasks()
        addNpmRule()
        addYarnRule()

        this.project.afterEvaluate {
            this.config.variant = new VariantBuilder( this.config ).build()
            configureSetupTask()
            configureNpmSetupTask()
            configureYarnSetupTask()
        }
    }

    private void addGlobalTypes()
    {
        addGlobalTaskType( NodeTask )
        addGlobalTaskType( NpmTask )
        addGlobalTaskType( YarnTask )
    }

    private void addTasks()
    {
        this.project.tasks.create( NpmInstallTask.NAME, NpmInstallTask )
        this.project.tasks.create( YarnInstallTask.NAME, YarnInstallTask )
        this.setupTask = this.project.tasks.create( SetupTask.NAME, SetupTask )
        this.npmSetupTask = this.project.tasks.create( NpmSetupTask.NAME, NpmSetupTask )
        this.yarnSetupTask = this.project.tasks.create( YarnSetupTask.NAME, YarnSetupTask )
    }

    private void addGlobalTaskType( Class type )
    {
        this.project.extensions.extraProperties.set( type.getSimpleName(), type )
    }

    private void addNpmRule()
    {
        // note this rule also makes it possible to specify e.g. "dependsOn npm_install"
        def workingDir
        this.project.afterEvaluate {
            workingDir = this.project.node.nodeModulesDir
        }
        project.getTasks().addRule( 'Pattern: "npm_<command>": Executes an NPM command.' ) { String taskName ->
            if ( taskName.startsWith( "npm_" ) )
            {
                NpmTask npmTask = project.getTasks().create( taskName, NpmTask.class )
                if ( workingDir )
                {
                    npmTask.afterEvaluate( workingDir )
                }

                String[] tokens = taskName.split( '_' ).tail() // all except first
                npmTask.npmCommand = tokens

                if ( tokens.head().equalsIgnoreCase( "run" ) )
                {
                    npmTask.dependsOn( NpmInstallTask.NAME )
                }

                return npmTask
            }
        }
    }

    private void addYarnRule()
    {
        // note this rule also makes it possible to specify e.g. "dependsOn yarn_install"
        project.getTasks().addRule( 'Pattern: "yarn_<command>": Executes an Yarn command.' ) { String taskName ->
            if ( taskName.startsWith( "yarn_" ) )
            {
                YarnTask yarnTask = project.getTasks().create( taskName, YarnTask.class )
                String[] tokens = taskName.split( '_' ).tail() // all except first
                yarnTask.yarnCommand = tokens

                if ( tokens.head().equalsIgnoreCase( "run" ) )
                {
                    yarnTask.dependsOn( YarnInstallTask.NAME )
                }

                return yarnTask
            }
        }
    }

    private void configureSetupTask()
    {
        this.setupTask.setEnabled( this.config.download )
    }

    private void configureNpmSetupTask()
    {
        this.npmSetupTask.configureVersion( this.config.npmVersion )
    }

    private void configureYarnSetupTask()
    {
        this.yarnSetupTask.configureVersion( this.config.yarnVersion )
    }
}
