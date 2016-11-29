package com.moowork.gradle.gulp

import com.moowork.gradle.node.NodePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class GulpPlugin
    implements Plugin<Project>
{
    static final String GULP_INSTALL_NAME = "installGulp";

    @Override
    void apply( final Project project )
    {
        project.plugins.apply( NodePlugin.class )

        project.extensions.create( GulpExtension.NAME, GulpExtension, project )

        project.extensions.extraProperties.set( 'GulpTask', GulpTask.class )
        project.tasks.create( GULP_INSTALL_NAME, GulpInstallTask.class )

        // note this rule also makes it possible to specify e.g. "dependsOn gulp_install"
        project.getTasks().addRule( 'Pattern: "gulp_<task>": Executes a named gulp task.' ) { String taskName ->
            if ( taskName.startsWith( 'gulp_' ) )
            {
                Task gulpTask = project.getTasks().create( taskName, GulpTask.class )
                gulpTask.args = [( taskName - 'gulp_' )]
                return gulpTask
            }
        }
    }
}
