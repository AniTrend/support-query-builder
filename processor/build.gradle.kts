plugins {
	id("co.anitrend.support.query.builder.plugin")
    alias(libs.plugins.google.devtools.ksp)
}

dependencies {
    implementation(project(":annotations"))

    api(libs.squareup.kotlinpoet)
    compileOnly(libs.androidx.room.common)
    implementation(libs.google.devtools.ksp.api)

    testImplementation(libs.jetbrains.kotlin.test)
    testImplementation(libs.kotlin.compile.testing.ksp)
}

tasks.withType(Test::class.java) {
    dependsOn(":annotations:classesJar")
}

tasks.test {
    useJUnitPlatform()
}
