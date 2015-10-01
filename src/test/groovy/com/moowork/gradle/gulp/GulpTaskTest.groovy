package com.moowork.gradle.gulp

import org.gradle.api.GradleException

class GulpTaskTest
    extends AbstractTaskTest
{
    def "gulp not installed"()
    {
        given:
        def task = this.project.tasks.create( 'simple', GulpTask )

        when:
        this.project.evaluate()
        task.exec()

        then:
        thrown( GradleException )
    }
}
