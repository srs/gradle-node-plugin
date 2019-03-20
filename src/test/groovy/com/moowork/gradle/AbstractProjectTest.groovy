package com.moowork.gradle

import org.gradle.api.internal.project.ProjectInternal
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class AbstractProjectTest
    extends Specification
{
    @Rule
    final TemporaryFolder temporaryFolder = new TemporaryFolder()

    ProjectInternal project

    File projectDir

    def setup()
    {
        this.projectDir = this.temporaryFolder.root;
        this.project = (ProjectInternal) ProjectBuilder.builder().
            withProjectDir( this.projectDir ).
            build()
    }
}
