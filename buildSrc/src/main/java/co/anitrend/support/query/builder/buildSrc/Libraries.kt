package co.anitrend.support.query.builder.buildSrc

import co.anitrend.support.query.builder.buildSrc.common.Versions
import co.anitrend.support.query.builder.buildSrc.module.Modules

object Libraries {

    const val junit = "junit:junit:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    object Android {

        object Tools {
            private const val version = "7.0.3"
            const val buildGradle = "com.android.tools.build:gradle:$version"
        }
    }

    object AndroidX {

        object Activity {
            private const val version = "1.4.0"
            const val activity = "androidx.activity:activity:$version"
            const val activityKtx = "androidx.activity:activity-ktx:$version"
        }

        object AppCompat {
            private const val version = "1.4.0-rc01"
            const val appcompat = "androidx.appcompat:appcompat:$version"
            const val appcompatResources = "androidx.appcompat:appcompat-resources:$version"
        }

        object Collection {
            private const val version = "1.2.0-alpha01"
            const val collection = "androidx.collection:collection:$version"
            const val collectionKtx = "androidx.collection:collection-ktx:$version"
        }

        object ConstraintLayout {
            private const val version = "2.1.1"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:$version"
            const val constraintLayoutSolver = "androidx.constraintlayout:constraintlayout-solver:$version"
        }
        
        object Fragment {
            private const val version = "1.3.0"
            const val fragment = "androidx.fragment:fragment:$version"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
            const val test = "androidx.fragment:fragment-ktx:fragment-testing$version"
        }

        object Lifecycle {
            private const val version = "2.4.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
            const val runTimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveDataCoreKtx = "androidx.lifecycle:lifecycle-livedata-core-ktx:$version"
        }

        object Navigation {
            private const val version = "2.3.5"
            const val common = "androidx.navigation:navigation-common:$version"
            const val commonKtx = "androidx.navigation:navigation-common-ktx:$version"

            const val dynamicFragment = "androidx.navigation:navigation-dynamic-features-fragment:$version"
            const val dynamicRuntime = "androidx.navigation:navigation-dynamic-features-runtime:$version"

            const val fragment = "androidx.navigation:navigation-fragment:$version"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"

            const val runtime = "androidx.navigation:navigation-runtime:$version"
            const val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:$version"

            const val safeArgsGenerator = "androidx.navigation:navigation-safe-args-generator:$version"
            const val safeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"

            const val test = "androidx.navigation:navigation-testing:$version"

            const val ui = "androidx.navigation:navigation-ui:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Room {
            private const val version = "2.2.6"
            const val compiler = "androidx.room:room-compiler:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val test = "androidx.room:room-testing:$version"
            const val ktx = "androidx.room:room-ktx:$version"
        }

        object SQLite {
            private const val version = "2.1.0"
            const val sqlite = "androidx.sqlite:sqlite:$version"
            const val framework = "androidx.sqlite:sqlite-framework:$version"
            const val ktx = "androidx.sqlite:sqlite-ktx:$version"
        }

        object Test {
            private const val version = "1.3.0"
            const val core = "androidx.test:core:$version"
            const val coreKtx = "androidx.test:core-ktx:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            object Extension {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit:$version"
                const val junitKtx = "androidx.test.ext:junit-ktx:$version"
            }
        }
    }

    object Google {

        object AutoService {
            private const val version = "1.0"
            const val autoService = "com.google.auto.service:auto-service:$version"
        }

        object Material {
            private const val version = "1.5.0-alpha05"
            const val material = "com.google.android.material:material:$version"
        }
    }

    object JetBrains {
        object Dokka {
            private const val version = "1.4.20"
            const val gradlePlugin = "org.jetbrains.dokka:dokka-gradle-plugin:$version"
        }

        object Kotlin {
            private const val version = "1.5.31"
            const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
            const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"

            object Gradle {
                const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
            }
        }
    }

    object Square {
        object KotlinPoet {
            private const val version = "1.10.2"
            const val kotlinPoet = "com.squareup:kotlinpoet:$version"
        }
    }
}
