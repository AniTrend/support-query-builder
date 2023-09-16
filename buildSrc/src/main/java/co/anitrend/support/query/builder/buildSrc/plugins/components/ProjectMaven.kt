package co.anitrend.support.query.builder.buildSrc.plugins.components

import co.anitrend.support.query.builder.buildSrc.extension.isCoreExtModule
import co.anitrend.support.query.builder.buildSrc.extension.props
import co.anitrend.support.query.builder.buildSrc.extension.publishingExtension
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar

internal fun Project.configureMaven() {
    publishingExtension().publications {
        val component = components.findByName("java")

        logger.lifecycle("Configuring maven publication options for ${project.path}:maven with component -> ${component?.name}")
        create("maven", org.gradle.api.publish.maven.MavenPublication::class.java) {
            groupId = "co.anitrend.query.builder"
            artifactId = project.name
            version = props[co.anitrend.support.query.builder.buildSrc.plugins.components.PropertyTypes.VERSION]

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
