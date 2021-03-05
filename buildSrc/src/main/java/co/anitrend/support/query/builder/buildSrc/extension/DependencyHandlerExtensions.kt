package co.anitrend.support.query.builder.buildSrc.extension

import groovy.lang.Closure
import org.gradle.api.artifacts.dsl.DependencyHandler

private enum class DependencyType(val configurationName: String) {
    API("api"),
    COMPILE("compileOnly"),
    DEBUG("debugOnly"),
    KAPT("kapt"),
    IMPLEMENTATION("implementation"),
    RUNTIME("runtimeOnly"),
    TEST("testImplementation"),
    ANDROID_TEST("androidTestImplementation")
}

private fun DependencyHandler.addDependency(
    dependencyNotation: Any,
    dependencyType: DependencyType,
    configureClosure: Closure<*>? = null
) = when (configureClosure) {
    null -> add(dependencyType.configurationName, dependencyNotation)
    else -> add(dependencyType.configurationName, dependencyNotation, configureClosure)
}

/**
 * Adds a dependency to the given configuration, and configures the dependency using the given closure.
 *
 * @param dependencyNotation The dependency notation, in one of the notations described above.
 * @param configureClosure The closure to use to configure the dependency.
 *
 * @return The dependency.
 */
internal fun DependencyHandler.kapt(
    dependencyNotation: Any,
    configureClosure: Closure<*>? = null
) = addDependency(dependencyNotation, DependencyType.KAPT, configureClosure)

/**
 * Adds a dependency to the given configuration, and configures the dependency using the given closure.
 *
 * @param dependencyNotation The dependency notation, in one of the notations described above.
 * @param configureClosure The closure to use to configure the dependency.
 *
 * @return The dependency.
 */
internal fun DependencyHandler.api(
    dependencyNotation: Any,
    configureClosure: Closure<*>? = null
) = addDependency(dependencyNotation, DependencyType.API, configureClosure)

/**
 * Adds a dependency to the given configuration, and configures the dependency using the given closure.
 *
 * @param dependencyNotation The dependency notation, in one of the notations described above.
 * @param configureClosure The closure to use to configure the dependency.
 *
 * @return The dependency.
 */
internal fun DependencyHandler.compile(
    dependencyNotation: Any,
    configureClosure: Closure<*>? = null
) = addDependency(dependencyNotation, DependencyType.COMPILE, configureClosure)

/**
 * Adds a dependency to the given configuration, and configures the dependency using the given closure.
 *
 * @param dependencyNotation The dependency notation, in one of the notations described above.
 * @param configureClosure The closure to use to configure the dependency.
 *
 * @return The dependency.
 */
internal fun DependencyHandler.debug(
    dependencyNotation: Any,
    configureClosure: Closure<*>? = null
) = addDependency(dependencyNotation, DependencyType.DEBUG, configureClosure)

/**
 * Adds a dependency to the given configuration, and configures the dependency using the given closure.
 *
 * @param dependencyNotation The dependency notation, in one of the notations described above.
 * @param configureClosure The closure to use to configure the dependency.
 *
 * @return The dependency.
 */
internal fun DependencyHandler.implementation(
    dependencyNotation: Any,
    configureClosure: Closure<*>? = null
) = addDependency(dependencyNotation, DependencyType.IMPLEMENTATION, configureClosure)

/**
 * Adds a dependency to the given configuration, and configures the dependency using the given closure.
 *
 * @param dependencyNotation The dependency notation, in one of the notations described above.
 * @param configureClosure The closure to use to configure the dependency.
 *
 * @return The dependency.
 */
internal fun DependencyHandler.runtime(
    dependencyNotation: Any,
    configureClosure: Closure<*>? = null
) = addDependency(dependencyNotation, DependencyType.RUNTIME, configureClosure)

/**
 * Adds a dependency to the given configuration, and configures the dependency using the given closure.
 *
 * @param dependencyNotation The dependency notation, in one of the notations described above.
 * @param configureClosure The closure to use to configure the dependency.
 *
 * @return The dependency.
 */
internal fun DependencyHandler.test(
    dependencyNotation: Any,
    configureClosure: Closure<*>? = null
) = addDependency(dependencyNotation, DependencyType.TEST, configureClosure)

/**
 * Adds a dependency to the given configuration, and configures the dependency using the given closure.
 *
 * @param dependencyNotation The dependency notation, in one of the notations described above.
 * @param configureClosure The closure to use to configure the dependency.
 *
 * @return The dependency.
 */
internal fun DependencyHandler.androidTest(
    dependencyNotation: Any,
    configureClosure: Closure<*>? = null
) = addDependency(dependencyNotation, DependencyType.ANDROID_TEST, configureClosure)