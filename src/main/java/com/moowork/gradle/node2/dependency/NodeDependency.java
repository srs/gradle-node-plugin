package com.moowork.gradle.node2.dependency;

import java.io.File;

/**
 * TODO: Remove the dependency. No need.
 *
 * - Add method for findExecInPath("node"), getUnpackDir(Platform). getExecutable(Platform).
 * - PlatformHelper.getUnpackDir(...)
 * 
 *
 *
 */
public final class NodeDependency
    extends ToolDependency
{
    private File executable;

    private boolean windows;

    public File getExecutable()
    {
        return this.executable;
    }

    public void setExecutable( final File executable )
    {
        this.executable = executable;
    }

    public boolean isWindows()
    {
        return this.windows;
    }

    public void setWindows( final boolean windows )
    {
        this.windows = windows;
    }
}
