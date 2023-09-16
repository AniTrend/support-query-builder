package co.anitrend.support.query.builder.buildSrc.plugins.components

import co.anitrend.support.query.builder.buildSrc.extension.libs
import co.anitrend.support.query.builder.buildSrc.extension.spotlessExtension
import org.gradle.api.Project

internal fun Project.configureSpotless() {
    spotlessExtension().run {
        kotlin {
            target("**/kotlin/**/*.kt")
            targetExclude(
                "${buildDir}/**/*.kt",
                "**/androidTest/**/*.kt",
                "**/test/**/*.kt",
                "bin/**/*.kt"
            )
            ktlint(libs.versions.ktlint.get().toString())
                .userData(mapOf("android" to "true",))
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
    }
}
