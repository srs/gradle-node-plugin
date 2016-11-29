package com.moowork.gradle.node

import com.moowork.gradle.node.variant.Variant
import org.gradle.api.Project
import org.gradle.api.Action
import org.gradle.api.credentials.Credentials
import org.gradle.api.artifacts.repositories.PasswordCredentials

class NodeExtension
{
    final static String NAME = 'node'

    def File workDir

    def File npmWorkDir

    def File yarnWorkDir

    def File nodeModulesDir

    def String version = '4.4.0'

    def String npmVersion = ''

    def String yarnVersion = ''

    def String distBaseUrl = 'https://nodejs.org/dist'

    def Action<? super PasswordCredentials> distCredentialsAction = null

    def Class distCredentialsType = PasswordCredentials

    def String npmCommand = 'npm'

    def String yarnCommand = 'yarn'

    def boolean download = false

    def Variant variant

    NodeExtension( final Project project )
    {
        def cacheDir = new File( project.projectDir, '.gradle' )
        this.workDir = new File( cacheDir, 'nodejs' )
        this.npmWorkDir = new File( cacheDir, 'npm' )
        this.yarnWorkDir = new File( cacheDir, 'yarn' )
        this.nodeModulesDir = project.projectDir
    }

    static NodeExtension get( final Project project )
    {
        return project.extensions.getByType( NodeExtension )
    }

    void distCredentials( Class credentialsType, Action<? super Credentials> action )
    {
        this.distCredentialsType = credentialsType
        this.distCredentialsAction = action
    }

    void distCredentials( Action<? super PasswordCredentials> action )
    {
        this.distCredentials( this.distCredentialsType, action )
    }

    class DistCredentials implements PasswordCredentials
    {
        def private String username = null
        def private String password = null

        public String getUsername()
        {
            return this.username
        }

        public String getPassword()
        {
            return this.password
        }

        public void setUsername(String userName)
        {
            this.username = userName
        }

        public void setPassword(String password)
        {
            this.password = password
        }
    }
}
