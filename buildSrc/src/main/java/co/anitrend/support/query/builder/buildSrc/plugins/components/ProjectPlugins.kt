package co.anitrend.support.query.builder.buildSrc.plugins.components

import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import co.anitrend.support.query.builder.buildSrc.extension.isAppModule
import co.anitrend.support.query.builder.buildSrc.extension.matchesProcessorModule

private fun addAndroidPlugin(project: Project, pluginContainer: PluginContainer) {
    if (project.isAppModule())
        pluginContainer.apply("com.android.application")
    else if (project.matchesProcessorModule()) {
        pluginContainer.apply("kotlin")
    } else {
        pluginContainer.apply("com.android.library")
    }
}

private fun addKotlinAndroidPlugin(project: Project, pluginContainer: PluginContainer) {
    if (!project.matchesProcessorModule())
        pluginContainer.apply("kotlin-android")
}

private fun addAnnotationProcessor(project: Project, pluginContainer: PluginContainer) {
    if (project.isAppModule() || project.matchesProcessorModule())
        pluginContainer.apply("kotlin-kapt")
}


internal fun Project.configurePlugins() {
    addAndroidPlugin(project, plugins)
    addKotlinAndroidPlugin(project, plugins)
    addAnnotationProcessor(project, plugins)
}