package com.moowork.gradle.node.task

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.variant.Variant
import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.repositories.ArtifactRepository
import org.gradle.api.artifacts.repositories.IvyArtifactRepository
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class SetupTask
    extends DefaultTask
{
    public final static String NAME = 'nodeSetup'

    private NodeExtension config

    protected Variant variant

    private IvyArtifactRepository repo

    private List<ArtifactRepository> allRepos;

    SetupTask()
    {
        this.group = 'Node'
        this.description = 'Download and install a local node/npm version.'
        this.enabled = false
    }

    @Input
    public Set<String> getInput()
    {
        configureIfNeeded()

        def set = new HashSet<>()
        set.add( this.config.download )
        set.add( this.variant.archiveDependency )
        set.add( this.variant.exeDependency )
        return set
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
        this.variant = this.config.variant
    }

    @TaskAction
    void exec()
    {
        configureIfNeeded()
        addRepository()

        if ( this.variant.exeDependency )
        {
            copyNodeExe()
        }

        unpackNodeArchive()
        setExecutableFlag()
        restoreRepositories()
    }

    private void copyNodeExe()
    {
        this.project.copy {
            from getNodeExeFile()
            into this.variant.nodeBinDir
            rename 'node.+\\.exe', 'node.exe'
        }
    }

    private void unpackNodeArchive()
    {
        if ( getNodeArchiveFile().getName().endsWith( 'zip' ) )
        {
            this.project.copy {
                from this.project.zipTree( getNodeArchiveFile() )
                into getNodeDir().parent
            }
        }
        else if ( this.variant.exeDependency )
        {
            //Remap lib/node_modules to node_modules (the same directory as node.exe) because that's how the zip dist does it
            this.project.copy {
                from this.project.tarTree( getNodeArchiveFile() )
                into this.variant.nodeBinDir
                eachFile {
                    def m = it.path =~ /^.*?[\\/]lib[\\/](node_modules.*$)/
                    if ( m.matches() )
                    {
                        // remap the file to the root
                        it.path = m.group( 1 )
                    }
                    else
                    {
                        it.exclude()
                    }
                }
                includeEmptyDirs = false
            }
        }
        else
        {
            this.project.copy {
                from this.project.tarTree( getNodeArchiveFile() )
                into getNodeDir().parent
            }
        }
    }

    private void setExecutableFlag()
    {
        if ( !this.variant.windows )
        {
            new File( this.variant.nodeExec ).setExecutable( true )
        }
    }

    @Internal
    protected File getNodeExeFile()
    {
        return resolveSingle( this.variant.exeDependency )
    }

    @Internal
    protected File getNodeArchiveFile()
    {
        return resolveSingle( this.variant.archiveDependency )
    }

    private File resolveSingle( String name )
    {
        def dep = this.project.dependencies.create( name )
        def conf = this.project.configurations.detachedConfiguration( dep )
        conf.transitive = false
        return conf.resolve().iterator().next();
    }

    private void addRepository()
    {
        this.allRepos = new ArrayList<>()
        this.allRepos.addAll( this.project.repositories )
        this.project.repositories.clear()

        def distUrl = this.config.distBaseUrl
        this.repo = this.project.repositories.ivy {
            url distUrl
            layout 'pattern', {
                artifact 'v[revision]/[artifact](-v[revision]-[classifier]).[ext]'
                ivy 'v[revision]/ivy.xml'
            }
        }
    }

    private void restoreRepositories()
    {
        this.project.repositories.clear();
        this.project.repositories.addAll( this.allRepos );
    }
}
