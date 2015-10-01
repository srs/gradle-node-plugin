package com.moowork.gradle.grunt

import java.util.concurrent.atomic.AtomicBoolean
import nebula.test.PluginProjectSpec

class GruntPluginProjectSpec extends PluginProjectSpec {
    @Override
    String getPluginName() {
        return 'com.moowork.grunt'
    }

    def 'creates extension'() {
        when:
        project.apply plugin: pluginName

        then:
        project.extensions.getByName('grunt')
    }

    def 'can evaluate'() {
        setup:
        def signal = new AtomicBoolean(false)

        project.afterEvaluate {
            signal.getAndSet(true)
        }

        project.apply plugin: pluginName

        when:
        project.evaluate()

        then:
        noExceptionThrown()
        signal.get() == true
    }
}
