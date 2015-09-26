package com.moowork.gradle.node

import nebula.test.ProjectSpec
import spock.lang.Specification
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

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

    def "test nodejs repo base url"()
    {
        given:
        def response = new DefaultHttpClient().execute(new HttpGet(new NodeExtension(this.project).distBaseUrl))

        expect:
        response.getStatusLine().statusCode == 200
    }
}
