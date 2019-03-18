package com.moowork.gradle.node2.dependency;

import java.io.File;

public abstract class ToolDependency
{
    private File unpackDir;

    private String artifactDependency;

    public final File getUnpackDir()
    {
        return this.unpackDir;
    }

    public final void setUnpackDir( final File unpackDir )
    {
        this.unpackDir = unpackDir;
    }

    public final String getArtifactDependency()
    {
        return this.artifactDependency;
    }

    public final void setArtifactDependency( final String artifactDependency )
    {
        this.artifactDependency = artifactDependency;
    }
}
