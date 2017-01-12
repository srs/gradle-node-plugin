package com.moowork.gradle.node.yarn

import com.moowork.gradle.node.task.SetupTask
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Setup a specific version of Yarn to be used by the build.
 **/
class YarnSetupTask
    extends DefaultTask
{
    public final static String NAME = 'yarnSetup'

    private String yarnVersion = null

    public YarnSetupTask()
    {
        this.group = 'Node'
        this.description = 'Setup a specific version of Yarn to be used by the build.'
        dependsOn( [SetupTask.NAME] )

        this.project.afterEvaluate {
            getOutputs().dir( this.project.node.yarnWorkDir )
        }
    }

    void configureYarnVersion( String yarnVersion )
    {
        if ( !yarnVersion.isEmpty() )
        {
            logger.debug( "Setting yarnVersion to ${yarnVersion}" )
            this.yarnVersion = yarnVersion
            getInputs().property( 'yarnVersion', yarnVersion )
        }
        else
        {
            this.yarnVersion = null
        }
    }

    @TaskAction
    void exec()
    {
        String url
        String file
        if ( yarnVersion != null) {
            file = "yarn-v${yarnVersion}.tar.gz"
            url = "https://yarnpkg.com/downloads/$yarnVersion/$file"

        } else {
            file = "latest.tar.gz"
            url = "https://yarnpkg.com/latest.tar.gz"
        }

        File yarnDir = this.project.node.yarnWorkDir
        yarnDir.mkdirs()

        logger.debug( "Downloading yarn from ${url}" )

        this.project.ant.get( src: url, dest: yarnDir, skipexisting: true )

        this.project.copy {
            from project.tarTree( new File( yarnDir, file ) )
            into yarnDir
        }
    }
}
