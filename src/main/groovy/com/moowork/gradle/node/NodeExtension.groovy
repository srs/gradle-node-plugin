package com.moowork.gradle.node

import org.gradle.api.Project

class NodeExtension
{
    private final static String NAME = "node"

    def File workDir

    def String nodeVersion = "0.10.22"

    def String nodeDistUrl = "http://nodejs.org/dist"

    def String configName = "nodeDist"

    def boolean installNode = false

    static NodeExtension get( final Project project )
    {
        return project.extensions.getByType( NodeExtension )
    }

    static NodeExtension create( final Project project )
    {
        final NodeExtension ext = project.extensions.create( NAME, NodeExtension )
        ext.workDir = new File( project.rootDir, ".gradle/node" )
        return ext
    }
}
