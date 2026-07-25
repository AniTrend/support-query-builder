# gradle/

## Responsibility

Central Gradle metadata directory for dependency version alignment, wrapper distribution configuration, and publication version properties. It supports all project modules through shared dependency aliases and repository release metadata.

## Design Patterns

Uses Gradle version catalog centralization through `libs.versions.toml`, wrapper metadata isolation under `wrapper/`, and property-file based release metadata in `version.properties`. The version catalog acts as the dependency registry consumed by root build scripts, buildSrc convention plugins, and module build files.

## Data & Control Flow

Gradle loads `libs.versions.toml` during settings and build evaluation, then exposes plugin, library, and version aliases to build scripts. `buildSrc` imports the same catalog to compile convention plugin code against Android, Kotlin, Dokka, Spotless, and KSP APIs. Publication and manifest tasks read `version.properties` to stamp artifact version, code, and display name.

## Integration Points

Consumed by root `build.gradle.kts`, `buildSrc/settings.gradle.kts`, `buildSrc/build.gradle.kts`, module `build.gradle.kts` files, Gradle wrapper launchers, and release automation that updates `version.properties`.
