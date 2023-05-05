plugins {
	id("co.anitrend.support.query.builder.plugin")
}

dependencies {
	compileOnly(libs.androidx.room.runtime)

	implementation(libs.google.kotlin.symbol.processor)
	implementation(libs.squareup.kotlinpoet)
}
