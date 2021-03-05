package co.anitrend.support.query.builder.buildSrc.plugins


import co.anitrend.support.query.builder.buildSrc.plugins.components.configureAndroid
import co.anitrend.support.query.builder.buildSrc.plugins.components.configureKotlinJvm
import co.anitrend.support.query.builder.buildSrc.plugins.components.configureDependencies
import co.anitrend.support.query.builder.buildSrc.plugins.components.configurePlugins
import co.anitrend.support.query.builder.buildSrc.extension.matchesProcessorModule
import org.gradle.api.Plugin
import org.gradle.api.Project

open class CorePlugin : Plugin<Project> {

    /**
     * Inspecting available extensions
     */
    @Suppress("UnstableApiUsage")
    internal fun Project.availableExtensions() {
        val extensionSchema = project.extensions.extensionsSchema
        extensionSchema.forEach {
            println("Available extension for module ${project.path}: ${it.name} -> ${it.publicType}")
        }
    }

    /**
     * Inspecting available components
     */
    @Suppress("UnstableApiUsage")
    internal fun Project.availableComponents() {
        val collectionSchema = project.components.asMap
        collectionSchema.forEach {
            println("Available component for module ${project.path}: ${it.key} -> ${it.value}")
        }
    }

    /**
     * Apply this plugin to the given target object.
     *
     * @param target The target object
     */
    override fun apply(target: Project) {
        target.configurePlugins()
        target.availableExtensions()
        target.availableComponents()
        if (!target.matchesProcessorModule())
            target.configureAndroid()
        else target.configureKotlinJvm()
        target.configureDependencies()
    }
}
