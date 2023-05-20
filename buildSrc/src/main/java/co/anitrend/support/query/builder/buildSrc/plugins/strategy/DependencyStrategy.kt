package co.anitrend.support.query.builder.buildSrc.plugins.strategy

import co.anitrend.support.query.builder.buildSrc.extension.*
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

internal class DependencyStrategy(private val project: Project) {

    private fun DependencyHandler.applyDefaultDependencies() {
        implementation(project.libs.jetbrains.kotlin.stdlib.jdk8)
        implementation(project.libs.jetbrains.kotlin.reflect)

        test(project.libs.junit)
        test(project.libs.mockk)
    }

    private fun DependencyHandler.applyAndroidTestDependencies() {
        androidTest(project.libs.androidx.test.core)
        androidTest(project.libs.androidx.test.coreKtx)
        androidTest(project.libs.androidx.test.runner)
        androidTest(project.libs.androidx.test.rules)
        androidTest(project.libs.mockk.android)
    }

    private fun DependencyHandler.applyLifeCycleDependencies() {
        implementation(project.libs.androidx.lifecycle.extensions)
        implementation(project.libs.androidx.lifecycle.runTimeKtx)
        implementation(project.libs.androidx.lifecycle.liveDataKtx)
        implementation(project.libs.androidx.lifecycle.liveDataCoreKtx)
    }

    fun applyDependenciesOn(handler: DependencyHandler) {
        handler.applyDefaultDependencies()
        if (project.isAppModule())
            handler.applyLifeCycleDependencies()
        if (!project.isKotlinLibraryGroup())
            handler.applyAndroidTestDependencies()
    }
}