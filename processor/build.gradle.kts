import com.google.devtools.ksp.gradle.KspAATask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    compileOnly(libs.androidx.room.common)

    testImplementation(project(":annotations"))
    testImplementation(libs.androidx.room.common)
    testImplementation(libs.google.devtools.ksp)
    testImplementation(libs.google.devtools.ksp.api)
    testImplementation(libs.kotlin.compile.testing)
    testImplementation(libs.kotlin.compile.testing.ksp)
    testImplementation(libs.junit5.api)
    testRuntimeOnly(libs.junit5.engine)
}

// Ensure KSP tasks wait for annotations to be fully built
tasks.withType<KspAATask> {
    dependsOn(":annotations:jar")
    mustRunAfter(":annotations:classesJar")
}

// Ensure compilation tasks wait for annotations
tasks.withType<KotlinCompile> {
    dependsOn(":annotations:jar")
}

// Ensure test tasks wait for annotations
tasks.withType<Test> {
    dependsOn(":annotations:jar")
}

tasks.test {
    useJUnitPlatform()
    // Ensure test classpath includes annotations
    dependsOn(":annotations:jar")
}

tasks.withType<GenerateModuleMetadata> {
	dependsOn(":processor:classesJar")
}
