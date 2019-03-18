package com.moowork.gradle.node2.platform

import spock.lang.Specification
import spock.lang.Unroll

class PlatformResolverTest
    extends Specification
{
    private Properties props

    private PlatformResolver resolver

    def setup()
    {
        this.props = new Properties()
        this.resolver = new PlatformResolver( this.props )
    }

    @Unroll
    def "resolve platform for os '#osProp' and arch '#archProp'"()
    {
        given:
        this.props.setProperty( "os.name", osProp )
        this.props.setProperty( "os.arch", archProp )

        when:
        def p = this.resolver.get()

        then:
        p.name == name
        p.windows == isWindows

        where:
        osProp      | archProp | name         | isWindows
        'Windows 8' | 'x86'    | 'win-x86'    | true
        'Windows 8' | 'x86_64' | 'win-x64'    | true
        'Mac OS X'  | 'x86'    | 'darwin-x86' | false
        'Mac OS X'  | 'x86_64' | 'darwin-x64' | false
        'Linux'     | 'x86'    | 'linux-x86'  | false
        'Linux'     | 'x86_64' | 'linux-x64'  | false
        'SunOS'     | 'x86'    | 'sunos-x86'  | false
        'SunOS'     | 'x86_64' | 'sunos-x64'  | false
    }
}
