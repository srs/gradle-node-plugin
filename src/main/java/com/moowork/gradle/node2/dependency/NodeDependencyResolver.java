package com.moowork.gradle.node2.dependency;

import java.io.File;

import com.moowork.gradle.node2.platform.Platform;

public final class NodeDependencyResolver
    implements DependencyResolver<NodeDependency>
{
    private String nodeVersion;

    private Platform platform;

    private File cacheDir;

    public void setNodeVersion( final String nodeVersion )
    {
        this.nodeVersion = nodeVersion;
    }

    public void setPlatform( final Platform platform )
    {
        this.platform = platform;
    }

    public void setCacheDir( final File cacheDir )
    {
        this.cacheDir = cacheDir;
    }

    private String resolveDependencyString()
    {
        return "org.nodejs:node" + ":" + this.nodeVersion + ":" + this.platform.getName() + "@" +
            ( this.platform.isWindows() ? "zip" : "tar.gz" );
    }

    @Override
    public NodeDependency resolve()
    {
        final NodeDependency dep = new NodeDependency();
        dep.setArtifactDependency( resolveDependencyString() );
        dep.setUnpackDir( new File( this.cacheDir, "node" + File.separator + this.platform.getName() ) );
        dep.setWindows( this.platform.isWindows() );
        dep.setExecutable( new File( "/usr/local/bin/node" ) );
        return dep;
    }
}
