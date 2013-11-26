package com.moowork.gradle.node.task

import com.moowork.gradle.node.NodeExtension

class NpmTask extends NodeTask
{
    @Override
    void doExecute( )
    {
        def ext = NodeExtension.get( this.project )
        this.scriptFile = ext.variant.npmScriptFile
        super.doExecute()
    }
}
