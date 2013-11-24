package com.moowork.gradle.node.task

import com.moowork.gradle.node.util.FileLocationHelper

class NpmTask extends NodeTask
{
    @Override
    void doExecute( )
    {
        this.scriptFile = FileLocationHelper.getNpmScript( this.project )
        super.doExecute()
    }
}
