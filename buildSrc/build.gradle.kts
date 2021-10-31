import java.net.URI

plugins {
	`kotlin-dsl`
	`maven-publish`
}

repositories {
	google()
	jcenter()
	mavenCentral()
	maven {
		url = URI("https://www.jitpack.io")
	}
}

val kotlinVersion = "1.5.31"
val buildToolsVersion = "7.0.3"
val manesVersion = "0.33.0"

dependencies {
	/** Depend on the android gradle plugin, since we want to access it in our plugin */
	implementation("com.android.tools.build:gradle:$buildToolsVersion")
	
	/** Depend on the kotlin plugin, since we want to access it in our plugin */
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
	
	/** Dependency management */
	implementation("com.github.ben-manes:gradle-versions-plugin:$manesVersion")
	
	/* Depend on the default Gradle API's since we want to build a custom plugin */
	implementation(gradleApi())
	implementation(localGroovy())
}
