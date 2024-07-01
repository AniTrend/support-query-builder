package co.anitrend.support.query.builder.buildSrc.plugins.strategy

import co.anitrend.support.query.builder.buildSrc.extension.implementation
import co.anitrend.support.query.builder.buildSrc.extension.isSampleModule
import co.anitrend.support.query.builder.buildSrc.extension.libs
import co.anitrend.support.query.builder.buildSrc.extension.test
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

internal class DependencyStrategy(private val project: Project) {

    private fun DependencyHandler.applyDefaultDependencies() {
        implementation(project.libs.jetbrains.kotlin.stdlib.jdk8)
        implementation(project.libs.jetbrains.kotlin.reflect)

        test(project.libs.junit)
        test(project.libs.mockk)
        test(project.libs.jetbrains.kotlin.test)
    }

    private fun DependencyHandler.applyLifeCycleDependencies() {
        implementation(project.libs.androidx.lifecycle.extensions)
        implementation(project.libs.androidx.lifecycle.runTime.ktx)
        implementation(project.libs.androidx.lifecycle.liveData.ktx)
        implementation(project.libs.androidx.lifecycle.liveDataCore.ktx)
    }

    fun applyDependenciesOn(handler: DependencyHandler) {
        handler.applyDefaultDependencies()
        if (project.isSampleModule())
            handler.applyLifeCycleDependencies()
    }
}
