rootProject.name= "support-query-builder"
include(
	":core",
	":annotations",
	":processor",
	":processor-room"
)

if (!System.getenv().containsKey("CI"))
    include(":sample")
