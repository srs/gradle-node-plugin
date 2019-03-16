package com.moowork.gradle.node

import com.moowork.gradle.node.variant.Variant
import org.gradle.api.Project

class NodeExtension
{
    final static String NAME = 'node'

    File workDir

    File npmWorkDir

    File yarnWorkDir

    File nodeModulesDir

    String version = '10.15.3'

    String npmVersion = ''

    String yarnVersion = ''

    String distBaseUrl = 'https://nodejs.org/dist'

    String npmCommand = 'npm'

    String yarnCommand = 'yarn'

    boolean download = false

    Variant variant

    NodeExtension( final Project project )
    {
        def cacheDir = new File( project.projectDir, '.gradle' )
        this.workDir = new File( cacheDir, 'nodejs' )
        this.npmWorkDir = new File( cacheDir, 'npm' )
        this.yarnWorkDir = new File( cacheDir, 'yarn' )
        this.nodeModulesDir = project.projectDir
    }

    static NodeExtension get( final Project project )
    {
        return project.extensions.getByType( NodeExtension )
    }

    static NodeExtension create( final Project project )
    {
        return project.extensions.create( NAME, NodeExtension, project )
    }
}
