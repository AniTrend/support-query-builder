package co.anitrend.support.query.builder.buildSrc.plugins.components

import co.anitrend.support.query.builder.buildSrc.extension.isSampleModule
import co.anitrend.support.query.builder.buildSrc.extension.props
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile


private fun Project.configureAppAndroid() {
    extensions.configure<ApplicationExtension> {
        compileSdk = 36
        defaultConfig {
            applicationId = "co.anitrend.support.query.builder.sample"
            minSdk = 23
            targetSdk = 35
            versionCode = props[PropertyTypes.CODE].toInt()
            versionName = props[PropertyTypes.VERSION]
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildFeatures {
            viewBinding = true
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                isShrinkResources = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }

            getByName("debug") {
                isDebuggable = true
                isMinifyEnabled = false
                isShrinkResources = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        packaging {
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
            unitTests.isIncludeAndroidResources = true
            unitTests.isReturnDefaultValues = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
    }
}

private fun Project.configureLibraryAndroid() {
    extensions.configure<LibraryExtension> {
        compileSdk = 36
        defaultConfig {
            minSdk = 23
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }

            getByName("debug") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        packaging {
            resources.excludes.add("META-INF/NOTICE.*")
            resources.excludes.add("META-INF/LICENSE*")
        }

        sourceSets {
            map { androidSourceSet ->
                androidSourceSet.java.srcDir(
                    "src/${androidSourceSet.name}/kotlin"
                )
            }
            getByName("test") {
                resources.srcDirs(file("src/test/resources"))
            }
        }

        testOptions {
            unitTests.isIncludeAndroidResources = true
            unitTests.isReturnDefaultValues = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
    }
}

internal fun Project.configureAndroid() {
    if (isSampleModule()) {
        configureAppAndroid()
    } else {
        configureLibraryAndroid()
        configureLint()
    }

    tasks.withType(KotlinCompilationTask::class.java) {
        compilerOptions {
            allWarningsAsErrors.set(false)
            freeCompilerArgs.set(emptyList())
        }
    }

    tasks.withType(Test::class.java) {
        useJUnitPlatform()
        failOnNoDiscoveredTests.set(false)
    }

    tasks.withType(KotlinJvmCompile::class.java) {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
}

