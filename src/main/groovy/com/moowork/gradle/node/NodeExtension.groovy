package com.moowork.gradle.node

import com.moowork.gradle.node.variant.Variant
import org.gradle.api.Project

class NodeExtension
{
    final static String NAME = 'node'

    String nodeVersion = '0.10.12'

    String nodeDistBaseUrl = 'http://nodejs.org/dist'

    String configName = 'nodeDist'

    Variant variant = null

    static def NodeExtension get( final Project project )
    {
        return project.extensions.getByType( NodeExtension )
    }

    static def NodeExtension create( final Project project )
    {
        project.extensions.create( NAME, NodeExtension )
    }
}
