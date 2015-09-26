package com.moowork.gradle.node

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class AbstractProjectTest
    extends Specification
{
    @Rule
    TemporaryFolder tmpDir = new TemporaryFolder()

    Project project

    File projectDir

    def setup()
    {
        this.projectDir = this.tmpDir.root
        this.project = ProjectBuilder.builder().withProjectDir( this.projectDir ).build()
    }
}
