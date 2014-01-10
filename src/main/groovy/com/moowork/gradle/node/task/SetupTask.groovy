package com.moowork.gradle.node.task

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.variant.Variant
import com.moowork.gradle.node.variant.VariantBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class SetupTask
    extends DefaultTask
{
    public final static String NAME = 'nodeSetup'

    protected NodeExtension ext

    protected Variant variant

    SetupTask()
    {
        this.group = 'Node'
        this.description = 'Download and install a local node/npm version.'

        this.ext = NodeExtension.get( this.project )
        this.variant = VariantBuilder.build( this.ext )

        this.enabled = this.ext.download

        getOutputs().dir( this.variant.nodeDir )
    }

    @TaskAction
    void exec()
    {
        if ( this.variant.windows )
        {
            copyNodeExe()
        }

        unpackNodeTarGz()
    }

    private void copyNodeExe()
    {
        this.project.copy {
            from getNodeExeFile()
            into this.variant.nodeBinDir
            rename 'node.+\\.exe', 'node.exe'
        }
    }

    private void unpackNodeTarGz()
    {
        this.project.copy {
            from this.project.tarTree( getNodeTarGzFile() )
            into this.variant.nodeDir.parentFile
        }
    }

    protected File getNodeExeFile()
    {
        return findSingleFile( '.exe' )
    }

    protected File getNodeTarGzFile()
    {
        return findSingleFile( '.tar.gz' )
    }

    private File findSingleFile( final String suffix )
    {
        return this.project.configurations.getByName( NodeExtension.CONFIG_NAME ).files.find { it.name.endsWith( suffix ) }
    }
}
