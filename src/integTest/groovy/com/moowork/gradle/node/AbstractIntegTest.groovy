package com.moowork.gradle.node

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

abstract class AbstractIntegTest
    extends Specification
{
    @Rule
    TemporaryFolder projectDir = new TemporaryFolder()

    protected File buildFile

    def setup()
    {
        this.buildFile = this.projectDir.newFile( 'build.gradle' )

        def pluginClasspathResource = getClass().classLoader.getResource( "plugin-classpath.txt" )
        if ( pluginClasspathResource == null )
        {
            throw new IllegalStateException( "Did not find plugin classpath resource, run `testClasses` build task." )
        }

        def pluginClasspath = pluginClasspathResource.readLines()
            .collect { it.replace( '\\', '\\\\' ) } // escape backslashes in Windows paths
            .collect { "'$it'" }
            .join( ", " )

        this.buildFile << """
        buildscript {
            dependencies {
                classpath files($pluginClasspath)
            }
        }
        """
    }

    protected final GradleRunner gradleRunner( final String... arguments )
    {
        return GradleRunner.create().
            withProjectDir( this.projectDir.root ).
            withArguments( arguments )
    }

    protected final void writeFile( final String name, final String text )
    {
        File file = new File( this.projectDir.root, name )
        file.parentFile.mkdirs()
        file << text
    }

    protected final void writePackageJson( final String text )
    {
        writeFile( 'package.json', text )
    }

    protected final void writeEmptyPackageJson()
    {
        writePackageJson( """{
            "name": "example",
            "dependencies": {
            }
        }""" )
    }

    protected final void writeBuild( final String text )
    {
        this.buildFile << text
    }
}
