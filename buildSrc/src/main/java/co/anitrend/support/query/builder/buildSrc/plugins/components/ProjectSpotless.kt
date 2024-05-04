package co.anitrend.support.query.builder.buildSrc.plugins.components

import co.anitrend.support.query.builder.buildSrc.extension.libs
import co.anitrend.support.query.builder.buildSrc.extension.spotlessExtension
import org.gradle.api.Project

internal fun Project.configureSpotless() {
    spotlessExtension().run {
        val withLicenseHeader: (String) -> File = { extension ->
            rootProject.file("spotless/copyright$extension")
        }
        val buildDirectory = layout.buildDirectory.get()
        kotlin {
            target("**/*.kt")
            targetExclude(
                "${buildDirectory}/**/*.kt",
                "**/androidTest/**/*.kt",
                "**/test/**/*.kt",
                "bin/**/*.kt"
            )
            ktlint(libs.pintrest.ktlint.get().version)
            licenseHeaderFile(
                withLicenseHeader(".kt")
            )
        }
    }
}
