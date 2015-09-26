package com.moowork.gradle.node

import com.moowork.gradle.node.variant.Variant
import org.gradle.api.Project

class NodeExtension
{
    final static String NAME = 'node'

    final static String CONFIG_NAME = 'nodeDist'

    def File workDir

    def File nodeModulesDir

    def String version = '0.11.10'

    def String npmVersion = ''

    def String distBaseUrl = 'https://nodejs.org/dist'

    def String npmCommand = 'npm'

    def boolean download = false

    def Variant variant

    NodeExtension( final Project project )
    {
        this.workDir = new File( project.gradle.gradleUserHomeDir, 'nodejs' )
        this.nodeModulesDir = project.projectDir
    }

    static NodeExtension get( final Project project )
    {
        return project.extensions.getByType( NodeExtension )
    }
}
