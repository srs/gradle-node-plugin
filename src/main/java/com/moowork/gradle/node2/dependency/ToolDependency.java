package com.moowork.gradle.node2.dependency;

import java.io.File;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;

public abstract class ToolDependency
{
    private File unpackDir;

    private String artifactDependency;

    @OutputDirectory
    public final File getUnpackDir()
    {
        return this.unpackDir;
    }

    public final void setUnpackDir( final File unpackDir )
    {
        this.unpackDir = unpackDir;
    }

    @Input
    public final String getArtifactDependency()
    {
        return this.artifactDependency;
    }

    public final void setArtifactDependency( final String artifactDependency )
    {
        this.artifactDependency = artifactDependency;
    }
}
