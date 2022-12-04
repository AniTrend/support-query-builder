plugins {
	id("co.anitrend.support.query.builder.plugin")
}

dependencies {
	implementation(libs.google.auto.service)

	api(libs.squareup.kotlinpoet)
	compileOnly(libs.androidx.room.runtime)
}
