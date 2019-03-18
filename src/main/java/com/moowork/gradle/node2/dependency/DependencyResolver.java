package com.moowork.gradle.node2.dependency;

public interface DependencyResolver<T extends ToolDependency>
{
    T resolve();
}
