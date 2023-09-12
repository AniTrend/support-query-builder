plugins {
	id("co.anitrend.support.query.builder.plugin")
}

dependencies {
	compileOnly(libs.google.auto.service)
	kapt(libs.google.auto.service)

	api(libs.squareup.kotlinpoet)
	compileOnly(libs.androidx.room.common)
}
