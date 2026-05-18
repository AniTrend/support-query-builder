plugins {
	id("co.anitrend.support.query.builder.plugin")
    alias(libs.plugins.google.devtools.ksp)
}

dependencies {
    implementation(project(":annotations"))

    implementation(libs.google.auto.service)
    ksp(libs.auto.service.ksp)

    compileOnly(libs.google.devtools.ksp.api)
    compileOnly(libs.google.devtools.ksp)

    api(libs.squareup.kotlinpoet)
    implementation(libs.androidx.room.common)

    testImplementation(project(":annotations"))
    testImplementation(libs.androidx.room.common)
    testImplementation(libs.google.devtools.ksp)
    testImplementation(libs.google.devtools.ksp.api)
    testImplementation(libs.kotlin.compile.testing)
    testImplementation(libs.kotlin.compile.testing.ksp)
    testImplementation(libs.junit5.api)
    testRuntimeOnly(libs.junit5.engine)
}

tasks.test {
    dependsOn(tasks.named("generateMetadataFileForMavenPublication"))
    useJUnitPlatform()
}
