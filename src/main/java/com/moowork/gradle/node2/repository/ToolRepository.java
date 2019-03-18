package com.moowork.gradle.node2.repository;

import com.moowork.gradle.node2.dependency.ToolDependency;

public interface ToolRepository<T extends ToolDependency>
{
    void resolve( T dependency );
}
