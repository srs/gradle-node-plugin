package com.moowork.gradle.node.variant

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.util.PlatformHelper
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

    private Properties props

    def setup()
    {
        this.props = new Properties()
        PlatformHelper.INSTANCE = new PlatformHelper(this.props)
    }

    @Unroll
    def "test variant on windows version <4 (#osArch)"()
    {
        given:
          def project = ProjectBuilder.builder().build()

          this.props.setProperty("os.name", "Windows 8")
          this.props.setProperty("os.arch", osArch)

          def ext = new NodeExtension(project)
          ext.download = true
          ext.version = '0.11.1'
          ext.workDir = new File('.gradle/node').absoluteFile

          def builder = new VariantBuilder(ext)
          def variant = builder.build()

        expect:
          variant != null
          variant.windows
          variant.exeDependency == exeDependency
          variant.archiveDependency == 'org.nodejs:node:0.11.1:linux-x86@tar.gz'

          variant.nodeDir.toString().endsWith(NODE_BASE_PATH + nodeDir)
          variant.nodeBinDir.toString().endsWith(NODE_BASE_PATH + nodeDir)
          variant.nodeExec.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + "node.exe")
          variant.npmScriptFile.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + "node_modules${PS}npm${PS}bin${PS}npm-cli.js")

        where:
          osArch   | nodeDir                    | exeDependency
          'x86'    | 'node-v0.11.1-win-x86' | 'org.nodejs:node:0.11.1@exe'
          'x86_64' | 'node-v0.11.1-win-x64' | 'org.nodejs:x64/node:0.11.1@exe'
    }

    @Unroll
    def "test variant on windows version 4.+ with exe (#osArch)"()
    {
        given:
          def project = ProjectBuilder.builder().build()

          this.props.setProperty("os.name", "Windows 8")
          this.props.setProperty("os.arch", osArch)

          def ext = new NodeExtension(project)
          ext.download = true
          ext.version = '4.0.0'
          ext.workDir = new File('.gradle/node').absoluteFile

          def builder = new VariantBuilder(ext)
          def variant = builder.build()

        expect:
          variant != null
          variant.windows
          variant.exeDependency == exeDependency
          variant.archiveDependency == 'org.nodejs:node:4.0.0:linux-x86@tar.gz'

          variant.nodeDir.toString().endsWith(NODE_BASE_PATH + nodeDir)
          variant.nodeBinDir.toString().endsWith(NODE_BASE_PATH + nodeDir)
          variant.nodeExec.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + "node.exe")
          variant.npmScriptFile.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + "node_modules${PS}npm${PS}bin${PS}npm-cli.js")

        where:
          osArch   | nodeDir                   | exeDependency
          'x86'    | 'node-v4.0.0-win-x86' | 'org.nodejs:win-x86/node:4.0.0@exe'
          'x86_64' | 'node-v4.0.0-win-x64' | 'org.nodejs:win-x64/node:4.0.0@exe'
    }
    
    @Unroll
    def "test variant on windows without exe (#osArch)"()
    {
        given:
          def project = ProjectBuilder.builder().build()

          this.props.setProperty("os.name", "Windows 8")
          this.props.setProperty("os.arch", osArch)

          def ext = new NodeExtension(project)
          ext.download = true
          ext.version = version
          ext.workDir = new File('.gradle/node').absoluteFile

          def builder = new VariantBuilder(ext)
          def variant = builder.build()
          def nodeDir = "node-v${version}-${osArch}".toString()
          def depName = "org.nodejs:node:${version}:${osArch}@zip".toString()

        expect:
          variant != null
          variant.windows
          variant.exeDependency == null
          variant.archiveDependency == depName

          variant.nodeDir.toString().endsWith(NODE_BASE_PATH + nodeDir)
          variant.nodeBinDir.toString().endsWith(NODE_BASE_PATH + nodeDir)
          variant.nodeExec.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + "node.exe")
          variant.npmScriptFile.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + "node_modules${PS}npm${PS}bin${PS}npm-cli.js")
        where:
          version | osArch
          "4.5.0" | "win-x86"
          "6.2.1" | "win-x86"
          "7.0.0" | "win-x86"
          "4.5.0" | "win-x64"
          "6.2.1" | "win-x64"
          "7.0.0" | "win-x64"
    }

    @Unroll
    def "test variant on non-windows (#osName, #osArch)"()
    {
        given:
          this.props.setProperty("os.name", osName)
          this.props.setProperty("os.arch", osArch)

          def project = ProjectBuilder.builder().build()
          def ext = new NodeExtension(project)
          ext.download = true
          ext.version = '0.11.1'
          ext.workDir = new File('.gradle/node').absoluteFile

          def builder = new VariantBuilder(ext)
          def variant = builder.build()

        expect:
          variant != null
          !variant.windows
          variant.exeDependency == null
          variant.archiveDependency == depName

          variant.nodeDir.toString().endsWith(NODE_BASE_PATH + nodeDir)
          variant.nodeBinDir.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + 'bin')
          variant.nodeExec.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + "bin${PS}node")
          variant.npmScriptFile.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + "lib${PS}node_modules${PS}npm${PS}bin${PS}npm-cli.js")

        where:
          osName     | osArch   | nodeDir                   | depName
          'Linux'    | 'x86'    | 'node-v0.11.1-linux-x86'  | 'org.nodejs:node:0.11.1:linux-x86@tar.gz'
          'Linux'    | 'x86_64' | 'node-v0.11.1-linux-x64'  | 'org.nodejs:node:0.11.1:linux-x64@tar.gz'
          'Mac OS X' | 'x86'    | 'node-v0.11.1-darwin-x86' | 'org.nodejs:node:0.11.1:darwin-x86@tar.gz'
          'Mac OS X' | 'x86_64' | 'node-v0.11.1-darwin-x64' | 'org.nodejs:node:0.11.1:darwin-x64@tar.gz'
          'FreeBSD'  | 'x86'    | 'node-v0.11.1-linux-x86'  | 'org.nodejs:node:0.11.1:linux-x86@tar.gz'
          'FreeBSD'  | 'x86_64' | 'node-v0.11.1-linux-x64'  | 'org.nodejs:node:0.11.1:linux-x64@tar.gz'
          'SunOS'    | 'x86'    | 'node-v0.11.1-sunos-x86'  | 'org.nodejs:node:0.11.1:sunos-x86@tar.gz'
          'SunOS'    | 'x86_64' | 'node-v0.11.1-sunos-x64'  | 'org.nodejs:node:0.11.1:sunos-x64@tar.gz'
    }

    @Unroll
    def "test variant on ARM (#osName, #osArch, #sysOsArch)"()
    {
        given:
          this.props.setProperty("os.name", osName)
          this.props.setProperty("os.arch", osArch)

          def project = ProjectBuilder.builder().build()
          def ext = new NodeExtension(project)
          ext.download = true
          ext.version = '5.6.0'
          ext.workDir = new File('.gradle/node').absoluteFile

          def platformHelperSpy = Spy(PlatformHelper, constructorArgs: [this.props])
          platformHelperSpy.osArch >> { sysOsArch }
          def builder = new VariantBuilder(ext, platformHelperSpy)
          def variant = builder.build()

        expect:
          variant != null
          !variant.windows
          variant.exeDependency == null
          variant.archiveDependency == depName

          variant.nodeDir.toString().endsWith(NODE_BASE_PATH + nodeDir)
          variant.nodeBinDir.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + 'bin')
          variant.nodeExec.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + "bin${PS}node")
          variant.npmScriptFile.toString().endsWith(NODE_BASE_PATH + nodeDir + PS + "lib${PS}node_modules${PS}npm${PS}bin${PS}npm-cli.js")

        where:
          osName  | osArch | sysOsArch | nodeDir                    | depName
          'Linux' | 'arm'  | 'armv6l'  | 'node-v5.6.0-linux-armv6l' | 'org.nodejs:node:5.6.0:linux-armv6l@tar.gz'
          'Linux' | 'arm'  | 'armv7l'  | 'node-v5.6.0-linux-armv7l' | 'org.nodejs:node:5.6.0:linux-armv7l@tar.gz'
          'Linux' | 'arm'  | 'arm64'   | 'node-v5.6.0-linux-arm64'  | 'org.nodejs:node:5.6.0:linux-arm64@tar.gz'
    }

    @Unroll
    def "test npm paths on windows"()
    {
        given:
          this.props.setProperty("os.name", "Windows 8")
          this.props.setProperty("os.arch", "x86")
          def project = ProjectBuilder.builder().build()

          def ext = new NodeExtension(project)
          ext.download = download
          ext.npmVersion = npmVersion

          def builder = new VariantBuilder(ext)
          def variant = builder.build()

          def npmDir = variant.nodeDir
          def npm = ext.npmCommand + ".cmd"

          if (npmVersion != "") {
            npmDir = new File(ext.npmWorkDir, "npm-v${npmVersion}".toString())
          }

          if (download) {
            npm = new File(npmDir, npm).toString()
          }
        expect:
          variant.npmDir == npmDir
          variant.npmBinDir == npmDir
          variant.npmExec == npm

          // if no version use node paths
          npmVersion != "" || variant.npmDir == variant.nodeDir
          npmVersion != "" || variant.npmBinDir == variant.nodeBinDir

        where:
          download | npmVersion
          true     | "4.0.2"
          true     | ""
          false    | "4.0.2"
          false     | ""
    }

    @Unroll
    def "test npm paths on non-windows"()
    {
        given:
          this.props.setProperty("os.name", "Linux")
          this.props.setProperty("os.arch", "x86")
          def project = ProjectBuilder.builder().build()

          def ext = new NodeExtension(project)
          ext.download = download
          ext.npmVersion = npmVersion

          def builder = new VariantBuilder(ext)
          def variant = builder.build()

          def npmDir = variant.nodeDir
          def npm = ext.npmCommand

          if (npmVersion != "") {
            npmDir = new File(ext.npmWorkDir, "npm-v${npmVersion}".toString())
          }

          def npmBinDir = new File(npmDir, "bin")

          if (download) {
            npm = new File(npmBinDir, npm).toString()
          }
        expect:
          variant.npmDir == npmDir
          variant.npmBinDir == npmBinDir
          variant.npmExec == npm

          // if no version use node paths
          npmVersion != "" || variant.npmDir == variant.nodeDir
          npmVersion != "" || variant.npmBinDir == variant.nodeBinDir

        where:
          download | npmVersion
          true     | "4.0.2"
          true     | ""
          false    | "4.0.2"
          false    | ""
    }
}
