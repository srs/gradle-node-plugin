package com.moowork.gradle.node2.runtime;

import java.io.File;

import org.gradle.api.tasks.Input;

public interface NodeRuntime
{
    @Input
    File getExecutable();

    void execute( NodeCommand command );
}
