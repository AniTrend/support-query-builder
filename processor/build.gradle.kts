import co.anitrend.support.query.builder.buildSrc.Libraries

plugins {
	id("co.anitrend.support.query.builder.plugin")
}

dependencies {
	//implementation(project(":annotations"))

	implementation(Libraries.Google.AutoService.autoService)
	kapt(Libraries.Google.AutoService.autoService)

	implementation(Libraries.Square.KotlinPoet.kotlinPoet)
}
