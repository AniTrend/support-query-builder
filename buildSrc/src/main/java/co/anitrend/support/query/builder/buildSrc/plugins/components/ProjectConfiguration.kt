package co.anitrend.support.query.builder.buildSrc.plugins.components

import co.anitrend.support.query.builder.buildSrc.common.Configuration
import co.anitrend.support.query.builder.buildSrc.extension.*
import co.anitrend.support.query.builder.buildSrc.extension.baseAppExtension
import co.anitrend.support.query.builder.buildSrc.extension.baseExtension
import co.anitrend.support.query.builder.buildSrc.extension.libraryExtension
import co.anitrend.support.query.builder.buildSrc.extension.spotlessExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.io.File

private fun Project.configureLint() = libraryExtension().run {
    lint {
        abortOnError = false
        ignoreWarnings = false
        ignoreTestSources = true
    }
}

internal fun Project.configureSpotless() {
    if (!isAppModule())
        spotlessExtension().run {
            kotlin {
                target("**/kotlin/**/*.kt")
                targetExclude(
                    "$buildDir/**/*.kt",
                    "**/androidTest/**/*.kt",
                    "**/test/**/*.kt",
                    "bin/**/*.kt"
                )
                ktlint(libs.versions.ktlint.get().toString())
                    .userData(
                        mapOf(
                            "android" to "true",
                            "max_line_length" to "150",
                            "no-wildcard-imports" to "false"
                        )
                    ).editorConfigOverride(
                        mapOf(
                            "ij_kotlin_packages_to_use_import_on_demand" to "false"
                        )
                    )
                licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
            }
        }
}

@Suppress("UnstableApiUsage")
private fun DefaultConfig.applyAdditionalConfiguration(project: Project) {
    if (project.isAppModule()) {
        applicationId = "co.anitrend.support.query.builder.sample"
        project.baseAppExtension().buildFeatures {
            viewBinding = true
        }
    }
    else
        consumerProguardFiles.add(File("consumer-rules.pro"))
}

internal fun Project.configureAndroid(): Unit = baseExtension().run {
    compileSdkVersion(Configuration.compileSdk)
    defaultConfig {
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        applyAdditionalConfiguration(project)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            isTestCoverageEnabled = false
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            isTestCoverageEnabled = true
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/NOTICE.*")
        resources.excludes.add("META-INF/LICENSE*")
    }

    sourceSets {
        map { androidSourceSet ->
            androidSourceSet.java.srcDir(
                "src/${androidSourceSet.name}/kotlin"
            )
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    if (!isAppModule()) {
        configureLint()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType(KotlinCompile::class.java) {
        val compilerArgumentOptions = emptyList<String>()
		
        kotlinOptions {
            allWarningsAsErrors = false
            freeCompilerArgs = compilerArgumentOptions
        }
    }

    tasks.withType(KotlinJvmCompile::class.java) {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
