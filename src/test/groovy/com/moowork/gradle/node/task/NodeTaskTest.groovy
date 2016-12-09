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
        this.ext.download = false

        def task = this.project.tasks.create( 'simple', NodeTask )
        task.args = ['a', 'b']
        task.options = ['c', 'd']
        task.environment = ['a': '1']
        task.ignoreExitValue = true

        def script = new File( this.projectDir, 'script.js' )
        task.script = script
        task.workingDir = this.projectDir
        task.execOverrides = {}

        when:
        this.project.evaluate()
        task.exec()

        then:
        task.args == ['a', 'b']
        task.options == ['c', 'd']
        task.result.exitValue == 0
        1 * this.execSpec.setIgnoreExitValue( true )
        1 * this.execSpec.setEnvironment( ['a': '1'] )
        1 * this.execSpec.setExecutable( 'node' )
        1 * this.execSpec.setArgs( ['c', 'd', script.absolutePath, 'a', 'b'] )
    }

    def "exec node task (download)"()
    {
        def capturedEnv = [:]

        given:
        this.props.setProperty( 'os.name', 'Linux' )
        this.ext.download = true
        this.execSpec = Mock( ExecSpec )

        def task = this.project.tasks.create( 'simple', NodeTask )
        def script = new File( this.projectDir, 'script.js' )
        task.script = script

        when:
        this.project.evaluate()
        task.exec()

        then:
        task.result.exitValue == 0
        1 * this.execSpec.setIgnoreExitValue( false )
        1 * this.execSpec.setArgs( [script.absolutePath] )

        1 * this.execSpec.setEnvironment( _ ) >> { map -> capturedEnv = map
        }

        capturedEnv['PATH'] != null
    }

    def "exec node task (windows)"()
    {
        given:
        this.props.setProperty( 'os.name', 'Windows' )
        this.ext.download = false
        this.execSpec = Mock( ExecSpec )

        def task = this.project.tasks.create( 'simple', NodeTask )
        def script = new File( this.projectDir, 'script.js' )

        task.args = ['a', 'b']
        task.options = ['c', 'd']
        task.script = script

        when:
        this.project.evaluate()
        task.exec()

        then:
        task.result.exitValue == 0
        1 * this.execSpec.setIgnoreExitValue( false )
        1 * this.execSpec.setExecutable( 'node' )
        1 * this.execSpec.setArgs( ['c', 'd', script.absolutePath, 'a', 'b'] )
    }

    def "exec node task (windows download)"()
    {
        given:
        this.props.setProperty( 'os.name', 'Windows' )
        this.ext.download = true
        this.execSpec = Mock( ExecSpec )

        def task = this.project.tasks.create( 'simple', NodeTask )
        def script = new File( this.projectDir, 'script.js' )
        task.script = script

        when:
        this.project.evaluate()
        task.exec()

        then:
        task.result.exitValue == 0
        1 * this.execSpec.setIgnoreExitValue( false )
    }
}
