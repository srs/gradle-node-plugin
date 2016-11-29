package com.moowork.gradle.node.yarn

import com.moowork.gradle.node.exec.ExecRunner
import com.moowork.gradle.node.exec.NodeExecRunner
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.process.ExecResult

class YarnExecRunner
    extends ExecRunner
{
    public YarnExecRunner( final Project project )
    {
        super( project )
    }

    @Override
    protected ExecResult doExecute()
    {
        if ( this.ext.yarnCommand == 'yarn' && this.ext.variant.windows ) {
            this.ext.yarnCommand = 'yarn.cmd'
        }

        if ( !this.ext.download )
        {
            return run( this.ext.yarnCommand, this.arguments )
        }

        def String yarnScriptFile = "${this.project.node.yarnWorkDir}/node_modules/yarn/bin/yarn.js"
        def runner = new NodeExecRunner( this.project )
        runner.arguments = [yarnScriptFile] + this.arguments
        runner.environment = this.environment
        runner.workingDir = this.workingDir
        runner.execOverrides = this.execOverrides
        runner.ignoreExitValue = this.ignoreExitValue
        return runner.execute()
    }
}
