package com.moowork.gradle.gulp

import java.util.concurrent.atomic.AtomicBoolean
import nebula.test.PluginProjectSpec

class GulpPluginProjectSpec extends PluginProjectSpec {
    @Override
    String getPluginName() {
        return 'com.moowork.gulp'
    }

    def 'creates extension'() {
        when:
        project.apply plugin: pluginName

        then:
        project.extensions.getByName('gulp')
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
