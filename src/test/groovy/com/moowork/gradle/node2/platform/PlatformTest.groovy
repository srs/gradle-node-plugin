package com.moowork.gradle.node2.platform

import spock.lang.Specification

class PlatformTest
    extends Specification
{
    def "accessors"()
    {
        when:
        def p = new Platform( "darwin-x86" )

        then:
        p.name == "darwin-x86"
        !p.windows
    }

    def "is windows"()
    {
        when:
        def p = new Platform( "win-x86" )

        then:
        p.name == "win-x86"
        p.windows
    }
}
