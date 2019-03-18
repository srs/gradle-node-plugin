package com.moowork.gradle.node2.dependency

import spock.lang.Specification

class PackageDependencyTest
    extends Specification
{
    def "accessors"()
    {
        given:
        def dep = new PackageDependency()
        def unpackDir = new File( "." )

        when:
        dep.unpackDir = unpackDir
        dep.artifactDependency = "a:b:c"

        then:
        dep.unpackDir == unpackDir
        dep.artifactDependency == "a:b:c"
    }
}
