plugins {
	id("co.anitrend.support.query.builder.plugin")
}

android {
    namespace = "co.anitrend.support.query.builder.core.ext"
}

tasks.withType<com.android.build.gradle.internal.lint.AndroidLintAnalysisTask> {
    dependsOn(":core:classesJar")
}

dependencies {
    implementation(project(":core"))

    implementation(libs.androidx.sqliteKtx)
}
