import java.net.URI

plugins {
	`kotlin-dsl`
}

repositories {
	google()
	jcenter()
	mavenCentral()
	maven {
		url = URI("https://jitpack.io")
	}
	maven {
		url = URI("http://oss.sonatype.org/content/repositories/snapshots")
	}
}

kotlinDslPluginOptions {
	experimentalWarning.set(false)
}

val kotlinVersion = "1.4.31"
val buildToolsVersion = "4.1.2"
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
