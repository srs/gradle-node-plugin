package com.moowork.gradle.node2.repository;

import com.moowork.gradle.node2.dependency.ToolDependency;

public abstract class AbstractToolRepository<T extends ToolDependency>
    implements ToolRepository<T>
{
}
