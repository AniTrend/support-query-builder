package co.anitrend.support.query.builder.buildSrc.extension

import co.anitrend.support.query.builder.buildSrc.module.Modules
import co.anitrend.support.query.builder.buildSrc.plugins.components.PropertiesReader
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension


fun Project.isSampleModule() = name == Modules.App.Main.id
fun Project.isAnnotationModule() = name == Modules.Common.Annotation.id
fun Project.isCoreModule() = name == Modules.Common.Core.id
fun Project.isCoreExtModule() = name == Modules.Common.Ext.id
fun Project.isProcessorModule() = name == Modules.Processor.Kapt.id

fun Project.isKotlinLibraryGroup() = isProcessorModule() || isAnnotationModule() || isCoreModule()

val Project.libs get() = extensions.getByType<LibrariesForLibs>()

internal val Project.props: PropertiesReader
    get() = PropertiesReader(this)

internal fun Project.baseExtension() =
    extensions.getByType<BaseExtension>()

internal fun Project.baseAppExtension() =
    extensions.getByType<BaseAppModuleExtension>()

internal fun Project.libraryExtension() =
    extensions.getByType<LibraryExtension>()

internal fun Project.kotlinJvmExtension() =
    extensions.getByType<KotlinJvmProjectExtension>()

internal fun Project.publishingExtension() =
    extensions.getByType<PublishingExtension>()

internal fun Project.spotlessExtension() =
    extensions.getByType<SpotlessExtension>()

internal fun Project.versionCatalogExtension() =
    extensions.getByType<VersionCatalogsExtension>()
