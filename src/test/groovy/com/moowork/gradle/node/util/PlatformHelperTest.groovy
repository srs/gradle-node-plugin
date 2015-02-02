package com.moowork.gradle.node.util

import spock.lang.Specification
import spock.lang.Unroll

class PlatformHelperTest
    extends Specification
{
    @Unroll
    def "check os and architecture for #osProp (#archProp)"()
    {
        given:
        System.setProperty( "os.name", osProp )
        System.setProperty( "os.arch", archProp )

        expect:
        PlatformHelper.getOsName() == osName
        PlatformHelper.getOsArch() == osArch
        PlatformHelper.isWindows() == isWindows

        where:
        osProp      | archProp | osName    | osArch | isWindows
        'Windows 8' | 'x86'    | 'windows' | 'x86'  | true
        'Windows 8' | 'x86_64' | 'windows' | 'x64'  | true
        'Mac OS X'  | 'x86'    | 'darwin'  | 'x86'  | false
        'Mac OS X'  | 'x86_64' | 'darwin'  | 'x64'  | false
        'Linux'     | 'x86'    | 'linux'   | 'x86'  | false
        'Linux'     | 'x86_64' | 'linux'   | 'x64'  | false
    }

    def "throw exception if unsupported os"()
    {
        given:
        System.setProperty( "os.name", 'SunOS' )

        when:
        PlatformHelper.getOsName()

        then:
        thrown( IllegalArgumentException )
    }
}
