package com.moowork.gradle.node

import nebula.test.ProjectSpec
import spock.lang.Specification


class NodeExtensionTest
    extends ProjectSpec
{
    def "check whether npmCommand parameter is present in the node extension"() {
        when:
        this.project.apply plugin: 'com.moowork.node'

        then:
        this.project.extensions.node.npmCommand
    }

    def "check whether default npmCommand parameter value is equal to 'npm'"() {
        when:
        this.project.apply plugin: 'com.moowork.node'

        then:
        this.project.extensions.node.npmCommand == 'npm'
    }
}
