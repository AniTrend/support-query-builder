package co.anitrend.support.query.builder.buildSrc.plugins.components

import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import co.anitrend.support.query.builder.buildSrc.extension.isAppModule
import co.anitrend.support.query.builder.buildSrc.extension.isKotlinLibraryGroup
import co.anitrend.support.query.builder.buildSrc.extension.isProcessorModule

private fun addAndroidPlugin(project: Project, pluginContainer: PluginContainer) {
    when {
        project.isAppModule() -> pluginContainer.apply("com.android.application")
        project.isKotlinLibraryGroup() -> {
            pluginContainer.apply("maven-publish")
            pluginContainer.apply("kotlin")
        }
        else -> {
            pluginContainer.apply("maven-publish")
            pluginContainer.apply("com.android.library")
            pluginContainer.apply("org.jetbrains.dokka")
        }
    }
    if (!project.isAppModule())
        pluginContainer.apply("com.diffplug.spotless")
}

private fun addKotlinAndroidPlugin(project: Project, pluginContainer: PluginContainer) {
    if (!project.isKotlinLibraryGroup())
        pluginContainer.apply("kotlin-android")
}

private fun addAnnotationProcessor(project: Project, pluginContainer: PluginContainer) {
    if (project.isAppModule() || project.isProcessorModule())
        pluginContainer.apply("kotlin-kapt")
}


internal fun Project.configurePlugins() {
    addAndroidPlugin(project, plugins)
    addKotlinAndroidPlugin(project, plugins)
    addAnnotationProcessor(project, plugins)
}