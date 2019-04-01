package com.moowork.gradle.grunt

import org.gradle.api.Project

class GruntExtension
{
    final static String NAME = 'grunt'

    File workDir

    Boolean colors = true

    Boolean bufferOutput = false

    String gruntFile = 'Gruntfile.js'

    GruntExtension( final Project project )
    {
        this.workDir = project.projectDir
    }

    static GruntExtension get( final Project project )
    {
        return project.extensions.getByType( GruntExtension )
    }

    static GruntExtension create( final Project project )
    {
        def config = project.extensions.create( NAME, GruntExtension, project )
        if(project.rootProject.hasProperty( GruntExtension.NAME) ){
            def rootConfig = GruntExtension.get( project.rootProject )
            config.colors = rootConfig.colors
            config.bufferOutput = rootConfig.bufferOutput
            config.gruntFile = rootConfig.gruntFile
        }
        return config
    }
}
