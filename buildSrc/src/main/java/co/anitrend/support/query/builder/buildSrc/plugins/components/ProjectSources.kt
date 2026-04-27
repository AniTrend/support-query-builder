package co.anitrend.support.query.builder.buildSrc.plugins.components

import co.anitrend.support.query.builder.buildSrc.extension.*
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getValue


internal fun Project.configureSources() {
    val mainSourceSets = when {
        !isKotlinLibraryGroup() -> baseExtension().sourceSets["main"].java.srcDirs
        else -> kotlinJvmExtension().sourceSets["main"].kotlin.srcDirs()
    }

    val sourcesJar by tasks.register("sourcesJar", Jar::class.java) {
        archiveClassifier.set("sources")
        from(mainSourceSets)
    }

    val classesJar = if (isCoreModule()) {
        tasks.register("classesJar", Jar::class.java) {
            dependsOn(tasks.named("classes"))
            from(layout.buildDirectory.dir("classes/kotlin/main"))
            from(layout.buildDirectory.dir("classes/java/main"))
        }
    } else {
        null
    }

    artifacts {
        if (classesJar != null) {
            add("archives", classesJar)
        }
        add("archives", sourcesJar)
    }

    afterEvaluate {
        configureMaven()
    }
}
