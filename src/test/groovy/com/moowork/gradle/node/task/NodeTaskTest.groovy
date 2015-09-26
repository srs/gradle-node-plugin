package com.moowork.gradle.node.task

import com.moowork.gradle.node.AbstractProjectTest
import com.moowork.gradle.node.exec.NodeExecRunner
import org.gradle.process.ExecResult

class NodeTaskTest
    extends AbstractProjectTest
{
    def "exec node task"()
    {
        given:
        this.project.apply plugin: 'com.moowork.node'

        def task = this.project.tasks.create( 'simple', NodeTask )
        task.args = ['a', 'b']
        task.environment = ['a': '1']
        task.ignoreExitValue = true
        task.script = new File( this.projectDir, 'script.js' )
        task.workingDir = this.projectDir
        task.execOverrides = {}

        def result = Mock( ExecResult )
        result.exitValue >> 0

        task.runner = new NodeExecRunner( this.project ) {
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
