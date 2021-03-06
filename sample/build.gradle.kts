import co.anitrend.support.query.builder.buildSrc.Libraries

plugins {
	id("co.anitrend.support.query.builder.plugin")
}

dependencies {
	implementation(Libraries.AndroidX.Activity.activityKtx)
	implementation(Libraries.AndroidX.Fragment.fragmentKtx)
	implementation(Libraries.AndroidX.AppCompat.appcompat)
	implementation(Libraries.AndroidX.AppCompat.appcompatResources)

	implementation(Libraries.AndroidX.Room.ktx)
	implementation(Libraries.AndroidX.Room.runtime)
	kapt(Libraries.AndroidX.Room.compiler)

	implementation(project(":annotations"))
}
