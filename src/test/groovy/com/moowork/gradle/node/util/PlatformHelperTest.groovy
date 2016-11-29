package com.moowork.gradle.node.util

import spock.lang.Specification
import spock.lang.Unroll

class PlatformHelperTest
    extends Specification
{
    private Properties props

    private PlatformHelper helper

    def setup()
    {
        this.props = new Properties()
        PlatformHelper.INSTANCE = this.helper = new PlatformHelper( this.props )
    }

    @Unroll
    def "check os and architecture for #osProp (#archProp)"()
    {
        given:
        this.props.setProperty( "os.name", osProp )
        this.props.setProperty( "os.arch", archProp )

        expect:
        this.helper.getOsName() == osName
        this.helper.getOsArch() == osArch
        this.helper.isWindows() == isWindows

        where:
        osProp      | archProp | osName    | osArch | isWindows
        'Windows 8' | 'x86'    | 'win' | 'x86'  | true
        'Windows 8' | 'x86_64' | 'win' | 'x64'  | true
        'Mac OS X'  | 'x86'    | 'darwin'  | 'x86'  | false
        'Mac OS X'  | 'x86_64' | 'darwin'  | 'x64'  | false
        'Linux'     | 'x86'    | 'linux'   | 'x86'  | false
        'Linux'     | 'x86_64' | 'linux'   | 'x64'  | false
        'SunOS'     | 'x86'    | 'sunos'   | 'x86'  | false
        'SunOS'     | 'x86_64' | 'sunos'   | 'x64'  | false
    }

    def "throw exception if unsupported os"()
    {
        given:
        this.props.setProperty( "os.name", 'Nonsense' )

        when:
        this.helper.getOsName()

        then:
        thrown( IllegalArgumentException )
    }
}
