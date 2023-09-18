plugins {
	id("co.anitrend.support.query.builder.plugin")
}

tasks.withType<org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask> {
	dependsOn(":annotations:classesJar")
}

tasks.withType<GenerateModuleMetadata> {
    dependsOn(":processor:classesJar")
}

dependencies {
	compileOnly(libs.google.auto.service)
	kapt(libs.google.auto.service)

	api(libs.squareup.kotlinpoet)
	compileOnly(libs.androidx.room.common)

    implementation(project(":annotations"))
}
