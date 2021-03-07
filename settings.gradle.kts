rootProject.name= "support-query-builder"
include(
	":core",
	":annotations",
	":processor"
)

if (!System.getenv().containsKey("CI"))
    include(":sample")
