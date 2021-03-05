import co.anitrend.support.query.builder.buildSrc.Libraries

plugins {
	id("co.anitrend.support.query.builder.plugin")
}

dependencies {
	implementation(Libraries.AndroidX.SQLite.ktx)
}
