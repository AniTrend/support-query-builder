package co.anitrend.support.query.builder.buildSrc.plugins.components

import co.anitrend.support.query.builder.buildSrc.common.Configuration
import co.anitrend.support.query.builder.buildSrc.extension.*
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getValue


@Suppress("UnstableApiUsage")
internal fun Project.configureOptions() {
    if (!isAppModule() && !isKotlinLibraryGroup()) {
        val isCore = isCoreModule()
        val mainSourceSet = when {
            isCore -> baseExtension().sourceSets["main"].java.srcDirs
            else -> baseExtension().sourceSets["main"].kotlin.srcDirs()
        }

        val sourcesJar by tasks.register("sourcesJar", Jar::class.java) {
            archiveClassifier.set("sources")
            from(mainSourceSet)
        }

        val classesJar by tasks.register("classesJar", Jar::class.java) {
            from("${project.buildDir}/intermediates/classes/release")
        }

        artifacts {
            add("archives", classesJar)
            add("archives", sourcesJar)
        }

        publishingExtension().publications {
            val component = components.findByName("android")

            logger.info("Configuring maven publication options for ${project.path}:maven with component -> ${component?.name}")
            create("maven", MavenPublication::class.java) {
                groupId = "co.anitrend.query.builder"
                artifactId = project.name
                version = Configuration.versionName

                artifact(sourcesJar)
                if (isCore)
                    artifact("${project.buildDir}/outputs/aar/${project.name}-release.aar")
                else
                    artifact("${project.buildDir}/libs/${project.name}.jar")
                from(component)

                pom {
                    name.set("Support Query Builder")
                    description.set("A simple SQLite spec compliant query builder that integrates with room to create raw queries")
                    url.set("https://github.com/anitrend/support-query-builder")
                    licenses {
                        license {
                            name.set("Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("wax911")
                            name.set("Maxwell Mapako")
                            organizationUrl.set("https://github.com/anitrend")
                        }
                    }
                }
            }
        }
    }
}