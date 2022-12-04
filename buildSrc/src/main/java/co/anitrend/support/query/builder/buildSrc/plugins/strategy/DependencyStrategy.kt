package co.anitrend.support.query.builder.buildSrc.plugins.strategy

import co.anitrend.support.query.builder.buildSrc.extension.*
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

internal class DependencyStrategy(private val project: Project) {

    private fun DependencyHandler.applyDefaultDependencies() {
        implementation(project.library("jetbrains-kotlin-stdlib-jdk8"))
        implementation(project.library("jetbrains-kotlin-reflect"))

        test(project.library("junit"))
        test(project.library("mockk"))
        test(project.library("androidx-junitKtx"))
    }

    private fun DependencyHandler.applyAndroidTestDependencies() {
        androidTest(project.library("androidx-test-core"))
        androidTest(project.library("androidx-test-coreKtx"))
        androidTest(project.library("androidx-test-runner"))
        androidTest(project.library("androidx-test-rules"))
        androidTest(project.library("mockk-android"))
    }

    private fun DependencyHandler.applyLifeCycleDependencies() {
        implementation(project.library("androidx-lifecycle-extensions"))
        implementation(project.library("androidx-lifecycle-runTimeKtx"))
        implementation(project.library("androidx-lifecycle-liveDataKtx"))
        implementation(project.library("androidx-lifecycle-liveDataCoreKtx"))
    }

    fun applyDependenciesOn(handler: DependencyHandler) {
        handler.applyDefaultDependencies()
        if (project.isAppModule())
            handler.applyLifeCycleDependencies()
        if (!project.isKotlinLibraryGroup())
            handler.applyAndroidTestDependencies()
    }
}