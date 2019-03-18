package com.moowork.gradle.node2.repository;

import com.moowork.gradle.node2.dependency.PackageDependency;

public final class PackageRepositoryImpl
    extends AbstractToolRepository<PackageDependency>
    implements PackageRepository
{
    @Override
    public void resolve( final PackageDependency dependency )
    {
    }
}
