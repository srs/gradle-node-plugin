package com.moowork.gradle.node2.runner;

import java.io.File;

public final class NodeRunnerHelper
{
    public static File findExecutable()
    {
        final String pathEnv = System.getenv( "PATH" );
        if ( isWindows() )
        {
            return findExecutable( pathEnv, "node.exe" );
        }

        return findExecutable( pathEnv, "node" );
    }

    private static File findExecutable( final String pathEnv, final String name )
    {
        for ( final String path : pathEnv.split( File.pathSeparator ) )
        {
            final File file = new File( path, name );
            if ( file.isFile() && file.canExecute() )
            {
                return file;
            }
        }

        return null;
    }

    private static boolean isWindows()
    {
        final String name = System.getProperty( "os.name", "" ).toLowerCase();
        return name.contains( "windows" );
    }
}
