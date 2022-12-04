package co.anitrend.support.query.builder.buildSrc.extension

import co.anitrend.support.query.builder.buildSrc.module.Modules
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.getByType


fun Project.isAppModule() = name == Modules.App.Main.id
fun Project.isAnnotationModule() = name == Modules.Common.Annotation.id
fun Project.isCoreModule() = name == Modules.Common.Core.id
fun Project.isProcessorModule() = name == Modules.Processor.Core.id

fun Project.isKotlinLibraryGroup() = isProcessorModule() || isAnnotationModule()

fun Project.versionCatalog(): VersionCatalog =
    versionCatalogExtension()
        .named("libs")

fun Project.library(alias: String): Provider<MinimalExternalModuleDependency> =
    runCatching {
        versionCatalog()
            .findLibrary(alias)
            .get()
    }.getOrElse { error ->
        logger.error("Could not find module: `$alias` within version catalog", error)
        error("${error.message}, please check full logs for full details")
    }

fun Project.version(alias: String): VersionConstraint =
    runCatching {
        versionCatalog()
            .findVersion(alias)
            .get()
    }.getOrElse { error ->
        logger.error("Could not find version ref: `$alias` within version catalog", error)
        error("${error.message}, please check full logs for full details")
    }

internal fun Project.baseExtension() =
    extensions.getByType<BaseExtension>()

internal fun Project.baseAppExtension() =
    extensions.getByType<BaseAppModuleExtension>()

internal fun Project.libraryExtension() =
    extensions.getByType<LibraryExtension>()

internal fun Project.publishingExtension() =
    extensions.getByType<PublishingExtension>()

internal fun Project.spotlessExtension() =
    extensions.getByType<SpotlessExtension>()

internal fun Project.versionCatalogExtension() =
    extensions.getByType<VersionCatalogsExtension>()
