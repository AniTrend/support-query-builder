package co.anitrend.support.query.builder.buildSrc.plugins.components

import co.anitrend.support.query.builder.buildSrc.extension.isKotlinLibraryGroup
import co.anitrend.support.query.builder.buildSrc.extension.isSampleModule
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer

private fun addAndroidPlugin(project: Project, pluginContainer: PluginContainer) {
    when {
        project.isSampleModule() -> pluginContainer.apply("com.android.application")
        else -> {
            if (project.isKotlinLibraryGroup())
                pluginContainer.apply("kotlin")
            else
                pluginContainer.apply("com.android.library")

            pluginContainer.apply("maven-publish")
            pluginContainer.apply("org.jetbrains.dokka")
        }
    }
    if (!project.isSampleModule())
        pluginContainer.apply("com.diffplug.spotless")
}

internal fun Project.configurePlugins() {
    addAndroidPlugin(project, plugins)
}
