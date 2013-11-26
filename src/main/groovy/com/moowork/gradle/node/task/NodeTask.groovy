package com.moowork.gradle.node.task

import com.moowork.gradle.node.NodeExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class NodeTask extends DefaultTask
{
    protected File scriptFile

    protected List<String> scriptArgs

    NodeTask( )
    {
        this.group = 'Node'
        dependsOn( SetupTask.NAME )
        this.scriptArgs = new ArrayList<String>()
    }

    @TaskAction
    void exec( )
    {
        doExecute()
    }

    protected void doExecute( )
    {
        final ArrayList<String> list = new ArrayList<String>()
        list.add( getNodeExec().absolutePath )
        list.add( this.scriptFile.absolutePath )
        list.addAll( this.scriptArgs )

        this.project.exec {
            commandLine = list
        }
    }

    private File getNodeExec( )
    {
        return NodeExtension.get( this.project ).variant.nodeExec
    }

    void setScript( final File file )
    {
        this.scriptFile = file;
    }

    void setArgs( final String... args )
    {
        this.scriptArgs = Arrays.asList( args );
    }
}
