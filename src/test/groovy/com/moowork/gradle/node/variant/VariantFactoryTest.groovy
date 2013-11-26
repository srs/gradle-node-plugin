package com.moowork.gradle.node.variant

import com.moowork.gradle.node.NodeExtension
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.Unroll

class VariantFactoryTest extends Specification
{
    @Unroll
    def "test variant on windows (#osArch)"( )
    {
        given:
        System.setProperty( "os.name", "Windows 8" )
        System.setProperty( "os.arch", osArch )

        def project = ProjectBuilder.builder().build()
        def ext = NodeExtension.create( project )
        ext.nodeVersion = '0.11.1'

        def factory = new VariantFactory( project )
        def variant = factory.create()

        expect:
        variant != null
        variant.windows
        variant.exeDependency == ':node:0.11.1@exe'
        variant.tarGzDependency == ':node:0.11.1:linux-x86@tar.gz'
        variant.workDir.toString().endsWith( '/.gradle/node' )
        variant.nodeDir.toString().endsWith( '/.gradle/node/' + nodeDir )
        variant.nodeBinDir.toString().endsWith( '/.gradle/node/' + nodeDir + '/bin' )
        variant.nodeExec.toString().endsWith( '/.gradle/node/' + nodeDir + '/bin/node.exe' )
        variant.npmDir.toString().endsWith( '/.gradle/node/node-v0.11.1-linux-x86/lib/node_modules' )
        variant.npmScriptFile.toString().endsWith( '/.gradle/node/node-v0.11.1-linux-x86/lib/node_modules/npm/bin/npm-cli.js' )

        where:
        osArch   | nodeDir
        'x86'    | 'node-v0.11.1-windows-x86'
        'x86_64' | 'node-v0.11.1-windows-x64'
    }

    @Unroll
    def "test variant on non-windows (#osName, #osArch)"( )
    {
        given:
        System.setProperty( "os.name", osName )
        System.setProperty( "os.arch", osArch )

        def project = ProjectBuilder.builder().build()
        def ext = NodeExtension.create( project )
        ext.nodeVersion = '0.11.1'

        def factory = new VariantFactory( project )
        def variant = factory.create()

        expect:
        variant != null
        !variant.windows
        variant.exeDependency == null
        variant.tarGzDependency == depName
        variant.workDir.toString().endsWith( '/.gradle/node' )
        variant.nodeDir.toString().endsWith( '/.gradle/node/' + nodeDir )
        variant.nodeBinDir.toString().endsWith( '/.gradle/node/' + nodeDir + '/bin' )
        variant.nodeExec.toString().endsWith( '/.gradle/node/' + nodeDir + '/bin/node' )
        variant.npmDir.toString().endsWith( '/.gradle/node/' + nodeDir + '/lib/node_modules' )
        variant.npmScriptFile.toString().endsWith( '/.gradle/node/' + nodeDir + '/lib/node_modules/npm/bin/npm-cli.js' )

        where:
        osName     | osArch   | nodeDir                   | depName
        'Linux'    | 'x86'    | 'node-v0.11.1-linux-x86'  | ':node:0.11.1:linux-x86@tar.gz'
        'Linux'    | 'x86_64' | 'node-v0.11.1-linux-x64'  | ':node:0.11.1:linux-x64@tar.gz'
        'Mac OS X' | 'x86'    | 'node-v0.11.1-darwin-x86' | ':node:0.11.1:darwin-x86@tar.gz'
        'Mac OS X' | 'x86_64' | 'node-v0.11.1-darwin-x64' | ':node:0.11.1:darwin-x64@tar.gz'
    }
}
