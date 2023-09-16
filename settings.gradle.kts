pluginManagement {
	repositories {
		google()
		mavenCentral()
		maven {
			setUrl("https://www.jitpack.io")
		}
	}
}

rootProject.name= "support-query-builder"
include(
	":core",
	":core-ext",
	":annotations",
	":processor",
)

if (!System.getenv().containsKey("CI"))
    include(":sample")
