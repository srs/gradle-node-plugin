package com.moowork.gradle.node

import org.gradle.api.Project

class NodeExtension
{
    final static String NAME = 'node'

    final static String CONFIG_NAME = 'nodeDist'

    def File workDir

    def String version = '0.10.22'

    def String distBaseUrl = 'http://nodejs.org/dist'

    def boolean download = false

    NodeExtension( final Project project )
    {
        this.workDir = new File( project.buildDir, 'node' )
    }

    static NodeExtension get( final Project project )
    {
        return project.extensions.getByType( NodeExtension )
    }
}
