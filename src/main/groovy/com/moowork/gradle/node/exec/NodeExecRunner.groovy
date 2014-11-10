package com.moowork.gradle.node.exec
import org.gradle.api.Project
import org.gradle.process.ExecResult

class NodeExecRunner
    extends ExecRunner
{
    public NodeExecRunner( final Project project )
    {
        super( project )
    }

    @Override
    protected ExecResult doExecute()
    {
        def exec = 'node'
        if ( this.ext.download )
        {
            def nodeEnvironment = [:]
            def nodeBinDirPath = this.variant.nodeBinDir.getAbsolutePath()

            nodeEnvironment << System.getenv()
            nodeEnvironment['PATH'] = nodeBinDirPath + File.pathSeparator + System.getenv('PATH')

            this.environment = nodeEnvironment
            exec = this.variant.nodeExec
        }

        return run( exec, this.arguments )
    }
}
