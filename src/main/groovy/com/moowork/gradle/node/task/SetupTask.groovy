package com.moowork.gradle.node.task

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.variant.Variant
import com.moowork.gradle.node.variant.VariantBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class SetupTask
    extends DefaultTask
{
    public final static String NAME = 'nodeSetup'

    private NodeExtension config

    protected Variant variant

    SetupTask()
    {
        this.group = 'Node'
        this.description = 'Download and install a local node/npm version.'
        this.enabled = false
    }

    @InputFiles
    public Set<File> getDependencies()
    {
        configureIfNeeded()

        if ( !this.config.download )
        {
            return new HashSet<File>()
        }

        return this.project.configurations.getByName( NodeExtension.CONFIG_NAME ).files
    }

    @OutputDirectory
    public File getNodeDir()
    {
        configureIfNeeded()
        return this.variant.nodeDir
    }

    private void configureIfNeeded()
    {
        if ( this.config != null )
        {
            return
        }

        this.config = NodeExtension.get( this.project )
        this.variant = VariantBuilder.build( this.config )
    }

    @TaskAction
    void exec()
    {
        configureIfNeeded()
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
            into getNodeDir().parent
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
        return getDependencies().find { it.name.endsWith( suffix ) }
    }
}
