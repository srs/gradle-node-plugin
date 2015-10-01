package com.moowork.gradle.grunt

import org.gradle.api.Project

class GruntExtension
{
    final static String NAME = 'grunt'

    File workDir

    Boolean colors = true

    Boolean bufferOutput = false

    GruntExtension( final Project project )
    {
        this.workDir = project.projectDir
    }
}
