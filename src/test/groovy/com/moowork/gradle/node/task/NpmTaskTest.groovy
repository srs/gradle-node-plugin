package com.moowork.gradle.node.task

import com.moowork.gradle.node.AbstractProjectTest
import com.moowork.gradle.node.exec.NpmExecRunner
import org.gradle.process.ExecResult

class NpmTaskTest
    extends AbstractProjectTest
{
    def "exec npm task"()
    {
        given:
        this.project.apply plugin: 'com.moowork.node'

        def task = this.project.tasks.create( 'simple', NpmTask )
        task.args = ['a', 'b']
        task.environment = ['a': '1']
        task.ignoreExitValue = true
        task.workingDir = this.projectDir
        task.execOverrides = {}
        task.npmCommand = 'npm'

        def result = Mock( ExecResult )
        result.exitValue >> 0

        task.runner = new NpmExecRunner( this.project ) {
            @Override
            protected ExecResult doExecute()
            {
                return result
            }
        }
        task.runner.execute() >> result

        when:
        this.project.evaluate()
        task.exec()

        then:
        task.args == ['a', 'b']
        task.result.exitValue == 0
        task.runner.arguments
        task.runner.arguments.size() > 2
    }
}
