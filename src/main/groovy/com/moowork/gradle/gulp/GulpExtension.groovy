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
}
