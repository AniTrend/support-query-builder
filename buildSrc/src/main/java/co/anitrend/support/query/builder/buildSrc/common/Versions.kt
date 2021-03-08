package co.anitrend.support.query.builder.buildSrc.common

object Versions {

    private const val major = 0
    private const val minor = 1
    private const val patch = 1
    private const val candidate = 1

    private const val channel = "alpha"

    const val compileSdk = 30
    const val targetSdk = 30
    const val minSdk = 21

     /**
      * **RR**_X.Y.Z_
      * > **RR** reserved for build flavours and **X.Y.Z** follow the [versionName] convention
      */
    const val versionCode = major.times(10_000) +
             minor.times(1000) +
             patch.times(100) +
             candidate.times(10)

    /**
     * Naming schema: X.Y.Z-variant##
     * > **X**(Major).**Y**(Minor).**Z**(Patch)
     */
    const val versionName = "$major.$minor.$patch-$channel$candidate"

    const val mockk = "1.10.6"
    const val junit = "4.13.2"

    const val timber = "4.7.1"
}
