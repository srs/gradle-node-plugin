package com.moowork.gradle.node.util;

import org.gradle.util.GradleVersion;

class BackwardsCompat {
     private static boolean IS_GRADLE_MIN_45 = GradleVersion.current().compareTo(GradleVersion.version("4.5"))>=0;

    static boolean useMetadataSourcesRepository() {
        return IS_GRADLE_MIN_45
    }
}
