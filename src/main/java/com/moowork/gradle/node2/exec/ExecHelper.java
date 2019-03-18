package com.moowork.gradle.node2.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ExecHelper
{
    public static String exec( final String... cmd )
        throws IOException
    {
        final Process process = new ProcessBuilder( cmd ).start();

        final StringBuilder str = new StringBuilder();
        try (final BufferedReader input = new BufferedReader( new InputStreamReader( process.getInputStream() ) ))
        {
            String line;
            while ( ( line = input.readLine() ) != null )
            {
                str.append( line );
            }
        }

        return str.toString();
    }
}
