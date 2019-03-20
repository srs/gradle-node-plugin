package com.moowork.gradle.node2.runtime;

import org.gradle.api.Project;

import com.moowork.gradle.node2.dependency.NodeDependency;

public final class NodeRuntimeFactory
{
    private final Project project;

    public NodeRuntimeFactory( final Project project )
    {
        this.project = project;
    }

    public NodeRuntime create( final NodeDependency dependency )
    {
        return new NodeRuntimeImpl( this.project, dependency );
    }
}
