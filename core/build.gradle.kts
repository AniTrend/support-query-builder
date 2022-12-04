plugins {
	id("co.anitrend.support.query.builder.plugin")
}

android {
	namespace = "co.anitrend.support.query.builder.core"
}

dependencies {
	implementation(libs.androidx.sqliteKtx)
}
