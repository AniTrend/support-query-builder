package co.anitrend.support.query.builder.buildSrc.plugins.components

import co.anitrend.support.query.builder.buildSrc.plugins.strategy.DependencyStrategy
import co.anitrend.support.query.builder.buildSrc.extension.isProcessorModule
import co.anitrend.support.query.builder.buildSrc.module.Modules
import co.anitrend.support.query.builder.buildSrc.extension.implementation
import org.gradle.api.Project

internal fun Project.configureDependencies() {
    dependencies.implementation(
        fileTree("libs") {
            include("*.jar")
        }
    )
    DependencyStrategy(project).applyDependenciesOn(dependencies)
}
