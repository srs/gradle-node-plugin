package com.moowork.gradle

import nebula.test.IntegrationSpec

abstract class AbstractIntegTest
    extends IntegrationSpec
{
    def setup()
    {
        def pluginClasspathResource = getClass().classLoader.getResource( "plugin-classpath.txt" )
        if ( pluginClasspathResource == null )
        {
            throw new IllegalStateException( "Did not find plugin classpath resource, run 'functionalTestClasses' build task." )
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

    protected final void writeFile( final String name, final String text )
    {
        File file = createFile( name )
        file.parentFile.mkdirs()
        file << text
    }

    protected final void writePackageJson( final String text )
    {
        writeFile( 'package.json', text )
    }

    protected final void writeEmptyPackageJson()
    {
        writePackageJson( """ {
            "name": "example",
            "dependencies": {}
        }
        """ )
    }

    protected final void writeBuild( final String text )
    {
        this.buildFile << text
    }
}
