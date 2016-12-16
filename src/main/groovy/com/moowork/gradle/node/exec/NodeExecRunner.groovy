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
            def nodeBinDirPath = this.variant.nodeBinDir.getAbsolutePath()

            // Take care of Windows environments that may contain "Path" OR "PATH" - both existing
            // possibly (but not in parallel as of now)
            if ( environment['Path'] != null )
            {
                environment['Path'] = nodeBinDirPath + File.pathSeparator + environment['Path']
            }
            else
            {
                environment['PATH'] = nodeBinDirPath + File.pathSeparator + environment['PATH']
            }

            exec = this.variant.nodeExec
        }

        return run( exec, this.arguments )
    }
}
