package co.anitrend.support.query.builder.buildSrc.common

object Versions {

    private const val major = 0
    private const val minor = 1
    private const val patch = 4
    private const val candidate = 1

    private const val channel = "alpha"

    const val compileSdk = 31
    const val targetSdk = 31
    const val minSdk = 21

     /**
      * **RR**_X.Y.Z_
      * > **RR** reserved for build flavours and **X.Y.Z** follow the [versionName] convention
      */
    const val versionCode = major.times(1_000_000) +
             minor.times(10_000) +
             patch.times(100) +
             candidate

    /**
     * Naming schema: X.Y.Z-variant##
     * > **X**(Major).**Y**(Minor).**Z**(Patch)
     */
    val versionName = if (candidate < 1)
        "$major.$minor.$patch"
    else
        "$major.$minor.$patch-$channel$candidate"

    const val mockk = "1.12.0"
    const val junit = "4.13.2"
    const val ktlint = "0.40.0"
}
