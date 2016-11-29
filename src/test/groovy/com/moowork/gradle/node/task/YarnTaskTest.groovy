package com.moowork.gradle.node.task

import org.gradle.process.ExecSpec

class YarnTaskTest
    extends AbstractTaskTest
{
    def "exec yarn task"()
    {
        given:
        this.execSpec = Mock( ExecSpec )

        def task = this.project.tasks.create( 'simple', YarnTask )
        task.args = ['a', 'b']
        task.environment = ['a': '1']
        task.ignoreExitValue = true
        task.workingDir = this.projectDir
        task.execOverrides = {}

        when:
        this.project.evaluate()
        task.exec()

        then:
        task.args == ['a', 'b']
        task.result.exitValue == 0
        1 * this.execSpec.setIgnoreExitValue( true )
        1 * this.execSpec.setEnvironment( ['a': '1'] )
        1 * this.execSpec.setArgs( ['a', 'b'] )
    }

    def "exec npm task (download)"()
    {
        given:
        this.ext.download = true
        this.execSpec = Mock( ExecSpec )

        def task = this.project.tasks.create( 'simple', YarnTask )

        when:
        this.project.evaluate()
        task.exec()

        then:
        task.result.exitValue == 0
        1 * this.execSpec.setIgnoreExitValue( false )
    }
}
