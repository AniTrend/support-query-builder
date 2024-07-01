package co.anitrend.support.query.builder.buildSrc.plugins.components

import co.anitrend.support.query.builder.buildSrc.extension.baseAppExtension
import co.anitrend.support.query.builder.buildSrc.extension.baseExtension
import co.anitrend.support.query.builder.buildSrc.extension.isSampleModule
import co.anitrend.support.query.builder.buildSrc.extension.props
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.io.File

private fun DefaultConfig.applyAdditionalConfiguration(project: Project) {
    if (project.isSampleModule()) {
        applicationId = "co.anitrend.support.query.builder.sample"
        project.baseAppExtension().buildFeatures {
            viewBinding = true
        }
    }
    else
        consumerProguardFiles.add(File("consumer-rules.pro"))
}

internal fun Project.configureAndroid(): Unit = baseExtension().run {
    compileSdkVersion(34)
    defaultConfig {
        minSdk = 23
        targetSdk = 34
        versionCode = props[PropertyTypes.CODE].toInt()
        versionName = props[PropertyTypes.VERSION]
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
        if (!project.isSampleModule()) {
            getByName("test") {
                resources.srcDirs(file("src/test/resources"))
            }
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    if (!isSampleModule()) {
        configureLint()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType(KotlinCompilationTask::class.java) {
        val compilerArgumentOptions = emptyList<String>()

        compilerOptions {
            allWarningsAsErrors.set(false)
            freeCompilerArgs.addAll(compilerArgumentOptions)
        }
    }

    tasks.withType(Test::class.java) {
        useJUnitPlatform()
    }

    tasks.withType(KotlinJvmCompile::class.java) {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}
