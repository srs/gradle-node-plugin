package com.moowork.gradle.node.exec

import org.gradle.api.Project
import org.gradle.process.ExecResult

class NodeExecRunner
    extends ExecRunner
{

    private List<String> pathExtensions = []

    public NodeExecRunner( final Project project )
    {
        super( project )
    }

    public void setPathExtensions(List<String> pathExtensions) {
      this.pathExtensions = pathExtensions
    }

    public List<String> getPathExtensions() {
      return pathExtensions
    }

    @Override
    protected ExecResult doExecute()
    {
        def exec = 'node'
        if ( this.ext.download )
        {
            def nodeEnvironment = this.environment
            if ( nodeEnvironment == null )
            {
                nodeEnvironment = [:]
                nodeEnvironment << System.getenv()
            }

            def allExtensions = [this.variant.nodeBinDir.getAbsolutePath()] + this.pathExtensions
            def pathExtension = allExtensions.join(File.pathSeparator)

            // Take care of Windows environments that may contain "Path" OR "PATH" - both existing
            // possibly (but not in parallel as of now)
            if ( System.getenv( 'Path' ) != null )
            {
                nodeEnvironment['Path'] = pathExtension + File.pathSeparator + System.getenv( 'Path' )
            }
            else
            {
                nodeEnvironment['PATH'] = pathExtension + File.pathSeparator + System.getenv( 'PATH' )
            }

            this.environment = nodeEnvironment
            exec = this.variant.nodeExec
        }

        return run( exec, this.arguments )
    }
}
