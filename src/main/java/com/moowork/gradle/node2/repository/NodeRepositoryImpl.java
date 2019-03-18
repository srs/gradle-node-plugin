package com.moowork.gradle.node2.repository;

import com.moowork.gradle.node2.dependency.NodeDependency;

public final class NodeRepositoryImpl
    extends AbstractToolRepository<NodeDependency>
    implements NodeRepository
{
    @Override
    public void resolve( final NodeDependency dependency )
    {
    }
}
