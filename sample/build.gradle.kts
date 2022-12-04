plugins {
	id("co.anitrend.support.query.builder.plugin")
}

android {
	namespace = "co.anitrend.support.query.builder.sample"
}

dependencies {
	implementation(libs.androidx.activityKtx)
	implementation(libs.androidx.fragmentKtx)

	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.appcompatResources)

	implementation(libs.androidx.constraintLayout)

	implementation(libs.androidx.navigation.fragmentKtx)
	implementation(libs.androidx.navigation.uiKtx)

	implementation(libs.google.android.material)

	implementation(libs.androidx.room.ktx)
	implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

	implementation(project(":annotations"))
	implementation(project(":core"))
	kapt(project(":processor"))
}
