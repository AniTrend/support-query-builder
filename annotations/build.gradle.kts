plugins {
	id("co.anitrend.support.query.builder.plugin")
}

// Ensure Kotlin module metadata is generated properly
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        // Ensure module metadata is generated
        freeCompilerArgs.addAll(listOf(
            "-Xjvm-default=all",
            "-opt-in=kotlin.RequiresOptIn"
        ))
    }
}

// Ensure JAR task includes proper metadata
tasks.jar {
    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
    }
}

