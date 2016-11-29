package com.moowork.gradle.node

import com.moowork.gradle.node.variant.Variant
import org.gradle.api.Project

class NodeExtension
{
    final static String NAME = 'node'

    def File workDir

    def File npmWorkDir

    def File yarnWorkDir

    def File nodeModulesDir

    def String version = '4.4.0'

    def String npmVersion = ''

    def String yarnVersion = ''

    def String distBaseUrl = 'https://nodejs.org/dist'

    def String npmCommand = 'npm'

    def String yarnCommand = 'yarn'

    def boolean download = false

    def Variant variant

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
}
