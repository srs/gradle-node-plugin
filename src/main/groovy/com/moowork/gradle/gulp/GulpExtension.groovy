package com.moowork.gradle.gulp

import org.gradle.api.Project

class GulpExtension
{
    final static String NAME = 'gulp'

    File workDir

    Boolean colors = true

    Boolean bufferOutput = false

    GulpExtension( final Project project )
    {
        this.workDir = project.projectDir
    }

    static GulpExtension get( final Project project )
    {
        return project.extensions.getByType( GulpExtension )
    }

    static GulpExtension create( final Project project )
    {
        def config = project.extensions.create( NAME, GulpExtension, project )
        if(project.rootProject.hasProperty( GulpExtension.NAME) ){
            def rootConfig = GulpExtension.get( project.rootProject )
            config.colors = rootConfig.colors
            config.bufferOutput = rootConfig.bufferOutput
        }
        return config
    }
}
