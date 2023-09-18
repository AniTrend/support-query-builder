package co.anitrend.support.query.builder.buildSrc.plugins.components

import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import co.anitrend.support.query.builder.buildSrc.extension.isSampleModule
import co.anitrend.support.query.builder.buildSrc.extension.isProcessorModule
import co.anitrend.support.query.builder.buildSrc.extension.isKotlinLibraryGroup

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

private fun addKotlinAndroidPlugin(project: Project, pluginContainer: PluginContainer) {
    if (!project.isKotlinLibraryGroup())
        pluginContainer.apply("kotlin-android")
}

private fun addAnnotationProcessor(project: Project, pluginContainer: PluginContainer) {
    if (project.isSampleModule() || project.isProcessorModule())
        pluginContainer.apply("kotlin-kapt")
}


internal fun Project.configurePlugins() {
    addAndroidPlugin(project, plugins)
    addKotlinAndroidPlugin(project, plugins)
    addAnnotationProcessor(project, plugins)
}
