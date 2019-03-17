package com.moowork.gradle

import org.apache.commons.io.FileUtils
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

abstract class AbstractIntegTest
    extends Specification
{
    @Rule
    final TemporaryFolder temporaryFolder = new TemporaryFolder();

    def File projectDir;

    def File buildFile;

    def setup()
    {
        this.projectDir = this.temporaryFolder.root;
        this.buildFile = createFile( 'build.gradle' )
    }

    protected final GradleRunner newRunner( final String... args )
    {
        return GradleRunner.create().
            withProjectDir( this.projectDir ).
            withArguments( args ).
            withPluginClasspath();
    }

    protected final BuildResult build( final String... args )
    {
        return newRunner( args ).build();
    }

    protected final BuildResult buildAndFail( final String... args )
    {
        return newRunner( args ).buildAndFail();
    }

    protected final BuildTask buildTask( final String task )
    {
        return build( task ).task( ':' + task );
    }

    protected final File createFile( final String name )
    {
        return new File( this.temporaryFolder.getRoot(), name );
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

    protected final void writeEmptyPackageLockJson()
    {
        writeEmptyPackageLockJson( 'package-lock.json' )
    }

    protected final void writeEmptyPackageLockJson( final String name )
    {
        writeFile( name, """ {
            "name": "example",
            "lockfileVersion": 1
        }
        """ )
    }

    protected final void writeEmptyPackageJson()
    {
        writePackageJson( """ {
            "name": "example",
            "dependencies": {}
        }
        """ )

        writeEmptyPackageLockJson()
    }

    protected final void writeBuild( final String text )
    {
        this.buildFile << text
    }

    protected final File directory( String path, File baseDir = getProjectDir() )
    {
        return new File( baseDir, path ).with {
            mkdirs()
            it
        }
    }

    protected final File file( String path, File baseDir = getProjectDir() )
    {
        def splitted = path.split( '/' )
        def directory = splitted.size() > 1 ? directory( splitted[0..-2].join( '/' ), baseDir ) : baseDir
        def file = new File( directory, splitted[-1] )
        file.createNewFile()
        return file
    }

    protected void copyResources( String srcDir, String destination )
    {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource( srcDir );
        if ( resource == null )
        {
            throw new RuntimeException( "Could not find classpath resource: $srcDir" )
        }

        File destinationFile = file( destination )
        File resourceFile = new File( resource.toURI() )
        if ( resourceFile.file )
        {
            FileUtils.copyFile( resourceFile, destinationFile )
        }
        else
        {
            FileUtils.copyDirectory( resourceFile, destinationFile )
        }
    }

    protected final boolean fileExists( String path )
    {
        return new File( this.projectDir, path ).exists()
    }
}
