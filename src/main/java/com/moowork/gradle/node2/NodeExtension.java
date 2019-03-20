package com.moowork.gradle.node2;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

import com.moowork.gradle.node2.platform.Platform;
import com.moowork.gradle.node2.platform.PlatformResolver;
import com.moowork.gradle.node2.util.PropertyHelper;

public class NodeExtension
{
    private final static String NAME = "node";

    private final static String DEFAULT_NODE_VERSION = "10.15.3";

    private final static String DEFAULT_NODE_REPO_URL = "https://nodejs.org/dist";

    private final static String DEFAULT_NPM_REPO_URL = "https://registry.npmjs.org";

    private final Property<String> version;

    private final Property<String> nodeRepoUrl;

    private final Property<String> npmRepoUrl;

    private final Property<Boolean> useLocal;

    private final Property<Platform> platform;

    private final DirectoryProperty cacheDir;

    private final DirectoryProperty workDir;

    public NodeExtension( final Project project )
    {
        this.version = PropertyHelper.property( project, String.class, DEFAULT_NODE_VERSION );
        this.nodeRepoUrl = PropertyHelper.property( project, String.class, DEFAULT_NODE_REPO_URL );
        this.npmRepoUrl = PropertyHelper.property( project, String.class, DEFAULT_NPM_REPO_URL );
        this.useLocal = PropertyHelper.property( project, Boolean.class, false );
        this.platform = PropertyHelper.propertyWithSupplier( project, Platform.class, new PlatformResolver() );

        final File defCacheDir = new File( project.getProjectDir(), ".gradle" + File.separator + "nodejs" );
        this.cacheDir = PropertyHelper.dirProperty( project, defCacheDir );
        this.workDir = PropertyHelper.dirProperty( project, project.getProjectDir() );
    }

    public Property<String> getVersion()
    {
        return this.version;
    }

    public Property<Platform> getPlatform()
    {
        return this.platform;
    }

    public void setPlatform( final String value )
    {
        this.platform.set( new Platform( value ) );
    }

    public Property<String> getNodeRepoUrl()
    {
        return this.nodeRepoUrl;
    }

    public Property<String> getNpmRepoUrl()
    {
        return this.npmRepoUrl;
    }

    public Property<Boolean> getUseLocal()
    {
        return this.useLocal;
    }

    public void setDownload( final boolean value )
    {
        this.useLocal.set( !value );
    }

    public DirectoryProperty getCacheDir()
    {
        return this.cacheDir;
    }

    public DirectoryProperty getWorkDir()
    {
        return this.workDir;
    }

    public static NodeExtension get( final Project project )
    {
        return project.getExtensions().getByType( NodeExtension.class );
    }

    public static NodeExtension create( final Project project )
    {
        return project.getExtensions().create( NAME, NodeExtension.class, project );
    }
}
