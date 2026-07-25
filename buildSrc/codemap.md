# buildSrc/

## Responsibility

Build logic module that compiles the repository's Gradle convention plugin. It centralizes Android, Kotlin, publishing, formatting, and dependency defaults used by project modules.

## Design Patterns

Uses Gradle `buildSrc` discovery, Kotlin DSL plugin implementation, and a version catalog bridge from `../gradle/libs.versions.toml`. The module depends on Android Gradle Plugin, Kotlin Gradle Plugin, Dokka, Spotless, Gradle API, and local Groovy so plugin code can configure those extensions directly.

## Data & Control Flow

`settings.gradle.kts` imports the shared catalog as `libs`. `build.gradle.kts` builds the plugin classpath, then Gradle loads the plugin marker from `src/main/resources` and instantiates `CorePlugin` for modules that apply the plugin.

## Integration Points

Integrates with root Gradle builds through the plugin id `co.anitrend.support.query.builder.plugin`. Reads repository version data from `gradle/version.properties` and dependency aliases from the shared version catalog.
