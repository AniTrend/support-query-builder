import co.anitrend.support.query.builder.buildSrc.Libraries

plugins {
	id("co.anitrend.support.query.builder.plugin")
}

dependencies {

	implementation(Libraries.Google.AutoService.autoService)
	kapt(Libraries.Google.AutoService.autoService)

	api(Libraries.Square.KotlinPoet.kotlinPoet)
	compileOnly(Libraries.AndroidX.Room.runtime)
}
