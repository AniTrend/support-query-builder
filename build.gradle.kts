buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.jetbrains.kotlin.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://www.jitpack.io")
        }
    }
}
