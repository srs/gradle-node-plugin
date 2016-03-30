package com.moowork.gradle.node.exec

import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.process.ExecResult

class NpmExecRunner
    extends ExecRunner
{
    public NpmExecRunner( final Project project )
    {
        super( project )
    }

    @Override
    protected ExecResult doExecute()
    {
        if ( this.ext.npmCommand == 'npm' && this.ext.variant.windows ) {
            this.ext.npmCommand = 'npm.cmd'
        }

        if ( !this.ext.download )
        {
            return run( this.ext.npmCommand, this.arguments )
        }

        def String npmScriptFile = this.variant.npmScriptFile
        def File localNpm = project.file( new File( this.ext.nodeModulesDir, 'node_modules/npm/bin/npm-cli.js' ) )

        // Use locally-installed npm if available
        if ( localNpm.exists() )
        {
            npmScriptFile = localNpm.absolutePath
        }

        boolean notInstalling = !arguments.join(' ').startsWith('install')
        boolean configuredLocalNpm = !project.node.npmVersion.empty

        if ( !localNpm.exists() && configuredLocalNpm && notInstalling )
        {
            throw new InvalidUserDataException("""
                Could not run npm command - local npm not found but requested in gradle node configuration.
                A common reason for this is an npm-shrinkwrap.json file is present and un-installs npm.
                To resolve this, add npm with version '${project.node.npmVersion}' to your package.json.
            """.stripIndent())
        }

        def runner = new NodeExecRunner( this.project )
        runner.arguments = [npmScriptFile] + this.arguments
        runner.environment = this.environment
        runner.workingDir = this.workingDir
        runner.execOverrides = this.execOverrides
        runner.ignoreExitValue = this.ignoreExitValue
        return runner.execute()
    }
}
