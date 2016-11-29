package com.moowork.gradle.grunt

import org.gradle.api.GradleException

class GruntTaskTest
    extends AbstractTaskTest
{
    def "grunt not installed"()
    {
        given:
        def task = this.project.tasks.create( 'simple', GruntTask )

        when:
        this.project.evaluate()
        task.exec()

        then:
        thrown( GradleException )
    }
}
