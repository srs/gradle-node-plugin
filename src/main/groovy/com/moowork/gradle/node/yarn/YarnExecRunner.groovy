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
        if ( this.ext.download )
        {
            def yarnBinDir = this.variant.yarnBinDir.getAbsolutePath();

            def npmBinDir = this.variant.npmBinDir.getAbsolutePath();

            def nodeBinDir = this.variant.nodeBinDir.getAbsolutePath();

            def path = yarnBinDir + File.pathSeparator + npmBinDir + File.pathSeparator + nodeBinDir;

            // Take care of Windows environments that may contain "Path" OR "PATH" - both existing
            // possibly (but not in parallel as of now)
            if ( this.environment['Path'] != null )
            {
                this.environment['Path'] = path + File.pathSeparator + this.environment['Path']
            }
            else
            {
                this.environment['PATH'] = path + File.pathSeparator + this.environment['PATH']
            }
        }

        return run( this.variant.yarnExec, this.arguments )
    }
}
