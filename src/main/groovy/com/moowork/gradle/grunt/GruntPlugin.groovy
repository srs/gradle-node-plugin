package com.moowork.gradle.grunt

import com.moowork.gradle.node.NodePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class GruntPlugin
    implements Plugin<Project>
{
    static final String GRUNT_INSTALL_NAME = "installGrunt";

    @Override
    void apply( final Project project )
    {
        project.plugins.apply( NodePlugin.class )

        project.extensions.create( GruntExtension.NAME, GruntExtension, project )

        project.extensions.extraProperties.set( 'GruntTask', GruntTask.class )
        project.tasks.create( GRUNT_INSTALL_NAME, GruntInstallTask.class )

        // note this rule also makes it possible to specify e.g. "dependsOn grunt_install"
        project.getTasks().addRule( 'Pattern: "grunt_<task>": Executes a named grunt task.' ) { String taskName ->
            if ( taskName.startsWith( 'grunt_' ) )
            {
                Task gruntTask = project.getTasks().create( taskName, GruntTask.class )
                gruntTask.args = [( taskName - 'grunt_' )]
                return gruntTask
            }
        }
    }
}
