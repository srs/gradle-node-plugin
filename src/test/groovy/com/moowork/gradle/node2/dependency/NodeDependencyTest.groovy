package com.moowork.gradle.node2.dependency

import spock.lang.Specification

class NodeDependencyTest
    extends Specification
{
    def "accessors"()
    {
        given:
        def dep = new NodeDependency()
        def unpackDir = new File( "." )
        def executableFile = new File( "." )

        when:
        dep.unpackDir = unpackDir
        dep.executable = executableFile
        dep.artifactDependency = "a:b:c"
        dep.windows = true

        then:
        dep.unpackDir == unpackDir
        dep.executable == executableFile
        dep.artifactDependency == "a:b:c"
        dep.windows
    }
}
