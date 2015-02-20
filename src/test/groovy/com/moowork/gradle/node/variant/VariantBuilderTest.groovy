package com.moowork.gradle.node.variant

import com.moowork.gradle.node.NodeExtension
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.Unroll

class VariantBuilderTest
    extends Specification
{

    /* OS dependant line separator */
    static final String PS = File.separator
    /* Relative base path for nodejs installation */
    static final String NODE_BASE_PATH = "${PS}.gradle${PS}node${PS}"

    @Unroll
    def "test variant on windows (#osArch)"()
    {
        given:
        def project = ProjectBuilder.builder().build()

        System.setProperty( "os.name", "Windows 8" )
        System.setProperty( "os.arch", osArch )

        def ext = new NodeExtension( project )
        ext.version = '0.11.1'
        ext.workDir = new File( '.gradle/node' ).absoluteFile

        def variant = VariantBuilder.build( ext )

        expect:
        variant != null
        variant.windows
        variant.exeDependency == 'org.nodejs:node:0.11.1@exe'
        variant.tarGzDependency == 'org.nodejs:node:0.11.1:linux-x86@tar.gz'

        variant.nodeDir.toString().endsWith( NODE_BASE_PATH + nodeDir )
        variant.nodeBinDir.toString().endsWith( NODE_BASE_PATH + nodeDir + PS + 'bin' )
        variant.nodeExec.toString().endsWith( NODE_BASE_PATH + nodeDir + PS + "bin${PS}node.exe" )
        variant.npmDir.toString().endsWith( NODE_BASE_PATH + "node-v0.11.1-linux-x86${PS}lib${PS}node_modules" )
        variant.npmScriptFile.toString().endsWith( NODE_BASE_PATH + "node-v0.11.1-linux-x86${PS}lib${PS}node_modules${PS}npm${PS}bin${PS}npm-cli.js" )

        where:
        osArch   | nodeDir
        'x86'    | 'node-v0.11.1-windows-x86'
        'x86_64' | 'node-v0.11.1-windows-x64'
    }

    @Unroll
    def "test variant on non-windows (#osName, #osArch)"()
    {
        given:
        System.setProperty( "os.name", osName )
        System.setProperty( "os.arch", osArch )

        def project = ProjectBuilder.builder().build()
        def ext = new NodeExtension( project )
        ext.version = '0.11.1'
        ext.workDir = new File( '.gradle/node' ).absoluteFile

        def variant = VariantBuilder.build( ext )

        expect:
        variant != null
        !variant.windows
        variant.exeDependency == null
        variant.tarGzDependency == depName

        variant.nodeDir.toString().endsWith( NODE_BASE_PATH + nodeDir )
        variant.nodeBinDir.toString().endsWith( NODE_BASE_PATH + nodeDir + PS + 'bin' )
        variant.nodeExec.toString().endsWith( NODE_BASE_PATH + nodeDir + PS + "bin${PS}node" )
        variant.npmDir.toString().endsWith( NODE_BASE_PATH + nodeDir + PS + "lib${PS}node_modules" )
        variant.npmScriptFile.toString().endsWith( NODE_BASE_PATH + nodeDir + PS + "lib${PS}node_modules${PS}npm${PS}bin${PS}npm-cli.js" )

        where:
        osName     | osArch   | nodeDir                   | depName
        'Linux'    | 'x86'    | 'node-v0.11.1-linux-x86'  | 'org.nodejs:node:0.11.1:linux-x86@tar.gz'
        'Linux'    | 'x86_64' | 'node-v0.11.1-linux-x64'  | 'org.nodejs:node:0.11.1:linux-x64@tar.gz'
        'Mac OS X' | 'x86'    | 'node-v0.11.1-darwin-x86' | 'org.nodejs:node:0.11.1:darwin-x86@tar.gz'
        'Mac OS X' | 'x86_64' | 'node-v0.11.1-darwin-x64' | 'org.nodejs:node:0.11.1:darwin-x64@tar.gz'
    }
}
