package co.anitrend.support.query.builder.buildSrc.plugins.strategy

import org.gradle.api.artifacts.dsl.DependencyHandler
import co.anitrend.support.query.builder.buildSrc.Libraries
import co.anitrend.support.query.builder.buildSrc.extension.*
import co.anitrend.support.query.builder.buildSrc.extension.implementation
import co.anitrend.support.query.builder.buildSrc.extension.isAppModule
import co.anitrend.support.query.builder.buildSrc.extension.matchesProcessorModule
import co.anitrend.support.query.builder.buildSrc.extension.test
import org.gradle.api.Project

internal class DependencyStrategy(private val project: Project) {

    private fun DependencyHandler.applyDefaultDependencies() {
        implementation(Libraries.JetBrains.Kotlin.stdlib)

        test(Libraries.junit)
        test(Libraries.mockk)
    }

    private fun DependencyHandler.applyAndroidTestDependencies() {
        androidTest(Libraries.AndroidX.Test.coreKtx)
        androidTest(Libraries.AndroidX.Test.rules)
        androidTest(Libraries.AndroidX.Test.runner)
        androidTest(Libraries.AndroidX.Test.Extension.junitKtx)
        androidTest(Libraries.mockk)
    }

    private fun DependencyHandler.applyLifeCycleDependencies() {
        implementation(Libraries.AndroidX.Lifecycle.liveDataCoreKtx)
        implementation(Libraries.AndroidX.Lifecycle.runTimeKtx)
        implementation(Libraries.AndroidX.Lifecycle.liveDataKtx)
        implementation(Libraries.AndroidX.Lifecycle.extensions)
    }

    fun applyDependenciesOn(handler: DependencyHandler) {
        handler.applyDefaultDependencies()
        if (project.isAppModule())
            handler.applyLifeCycleDependencies()
        if (!project.matchesProcessorModule())
            handler.applyAndroidTestDependencies()
    }
}