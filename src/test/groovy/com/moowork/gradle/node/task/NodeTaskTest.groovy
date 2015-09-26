package com.moowork.gradle.node.task

import org.gradle.process.ExecSpec

class NodeTaskTest
    extends AbstractTaskTest
{
    def "script not set"()
    {
        given:
        def task = this.project.tasks.create( 'simple', NodeTask )

        when:
        this.project.evaluate()
        task.exec()

        then:
        thrown( IllegalStateException )
    }

    def "exec node task"()
    {
        given:
        this.props.setProperty( 'os.name', 'Linux' )
        this.execSpec = Mock( ExecSpec )

        def task = this.project.tasks.create( 'simple', NodeTask )
        task.args = ['a', 'b']
        task.environment = ['a': '1']
        task.ignoreExitValue = true
        task.script = new File( this.projectDir, 'script.js' )
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

        // TODO: Better assertions
    }

    def "exec node task (download)"()
    {
        given:
        this.props.setProperty( 'os.name', 'Linux' )
        this.ext.download = true
        this.execSpec = Mock( ExecSpec )

        def task = this.project.tasks.create( 'simple', NodeTask )
        task.script = new File( this.projectDir, 'script.js' )

        when:
        this.project.evaluate()
        task.exec()

        then:
        task.result.exitValue == 0
        1 * this.execSpec.setIgnoreExitValue( false )

        // TODO: Better assertions
    }

    def "exec node task (windows download)"()
    {
        given:
        this.props.setProperty( 'os.name', 'Windows' )
        this.ext.download = true
        this.execSpec = Mock( ExecSpec )

        def task = this.project.tasks.create( 'simple', NodeTask )
        task.script = new File( this.projectDir, 'script.js' )

        when:
        this.project.evaluate()
        task.exec()

        then:
        task.result.exitValue == 0
        1 * this.execSpec.setIgnoreExitValue( false )

        // TODO: Better assertions
    }
}
