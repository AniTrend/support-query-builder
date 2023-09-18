plugins {
	id("co.anitrend.support.query.builder.plugin")
}

tasks.withType<GenerateModuleMetadata> {
    dependsOn(":core:classesJar")
}
