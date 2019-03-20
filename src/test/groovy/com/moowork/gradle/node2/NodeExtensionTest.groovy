package com.moowork.gradle.node2

import com.moowork.gradle.AbstractProjectTest

class NodeExtensionTest
    extends AbstractProjectTest
{
    def "check default values"()
    {
        when:
        def ext = NodeExtension.create( this.project )

        then:
        ext != null
        ext.version.get() == '10.15.3'
        ext.nodeRepoUrl.get() == 'https://nodejs.org/dist'
        ext.npmRepoUrl.get() == 'https://registry.npmjs.org'
        !ext.useLocal.get()
    }

    def "set platform"()
    {
        given:
        def ext = NodeExtension.create( this.project )

        when:
        ext.platform = "os-arch"

        then:
        ext.platform.get().name == "os-arch"
    }
}
