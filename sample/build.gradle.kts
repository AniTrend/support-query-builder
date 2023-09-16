plugins {
	id("co.anitrend.support.query.builder.plugin")
}

android {
	namespace = "co.anitrend.support.query.builder.sample"
}

tasks.withType<com.android.build.gradle.tasks.JavaPreCompileTask> {
	dependsOn(":annotations:classesJar", ":processor:classesJar", ":core:classesJar", ":core-ext:classesJar")
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

	androidTestImplementation(libs.androidx.test.coreKtx)
	androidTestImplementation(libs.androidx.test.rules)
	androidTestImplementation(libs.androidx.test.runner)
	androidTestImplementation(libs.mockk.android)

    implementation(project(":annotations"))
    implementation(project(":core"))
    implementation(project(":core-ext"))
    kapt(project(":processor"))
}
