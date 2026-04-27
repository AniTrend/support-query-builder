import com.google.devtools.ksp.gradle.KspAATask

plugins {
	id("co.anitrend.support.query.builder.plugin")
    alias(libs.plugins.google.devtools.ksp)
}
android {
	namespace = "co.anitrend.support.query.builder.sample"
}

tasks.withType<KspAATask> {
	dependsOn(":processor:classesJar")
}

tasks.withType<com.android.build.gradle.tasks.JavaPreCompileTask> {
	dependsOn(
		":annotations:classesJar",
		":processor:classesJar",
		":core:classesJar",
		":core:ext:classesJar",
	)
}

dependencies {
    implementation(project(":annotations"))
    implementation(project(":core"))
    implementation(project(":core:ext"))
    ksp(project(":processor"))

	implementation(libs.androidx.activity)
	implementation(libs.androidx.activity.ktx)
	implementation(libs.androidx.fragment)
	implementation(libs.androidx.fragment.ktx)

	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.appcompatResources)

	implementation(libs.androidx.constraintLayout)

	implementation(libs.androidx.navigation.fragment)
	implementation(libs.androidx.navigation.fragment.ktx)
	implementation(libs.androidx.navigation.ui)
	implementation(libs.androidx.navigation.ui.ktx)

	implementation(libs.google.android.material)

	implementation(libs.androidx.room.runtime)
	implementation(libs.androidx.room.common)
	implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

	androidTestImplementation(libs.androidx.test.core)
	androidTestImplementation(libs.androidx.test.rules)
	androidTestImplementation(libs.androidx.test.runner)
	androidTestImplementation(libs.mockk.android)
}
