// Top-level build file where you can add configuration options common to all sub-projects/modules.
import java.net.URI
import org.jetbrains.dokka.gradle.DokkaExtension

plugins {
    id("org.jetbrains.dokka")
}
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.jetbrains.kotlin.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://www.jitpack.io")
        }
    }
}

dokka {
    dokkaPublications.html {
        outputDirectory.set(rootProject.file("dokka-docs"))
        failOnWarning.set(false)
    }
}

subprojects {
    plugins.withId("org.jetbrains.dokka") {
        extensions.configure(DokkaExtension::class.java) {
            val modulePath = project.path.removePrefix(":").replace(":", "/")

            dokkaSourceSets.configureEach {
                reportUndocumented.set(true)
                skipEmptyPackages.set(true)

                sourceLink {
                    localDirectory.set(layout.projectDirectory.dir("src/main/kotlin"))
                    remoteUrl.set(URI("https://github.com/AniTrend/support-query-builder/tree/develop/$modulePath/src/main/kotlin"))
                    remoteLineSuffix.set("#L")
                }
            }
        }
    }
}

dependencies {
    dokka(project(":annotations"))
    dokka(project(":core"))
    dokka(project(":core:ext"))
}
