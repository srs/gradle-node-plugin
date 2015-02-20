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
            // Take care of Windows environments that may contain "Path" OR "PATH" - both existing
            // possibly (but not in parallel as of now)
            if (System.getenv('Path') != null) {
                nodeEnvironment['Path'] = nodeBinDirPath + File.pathSeparator + System.getenv('Path')
            } else {
                nodeEnvironment['PATH'] = nodeBinDirPath + File.pathSeparator + System.getenv('PATH')
            }


            this.environment = nodeEnvironment
            exec = this.variant.nodeExec
        }

        return run( exec, this.arguments )
    }
}
