# buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/plugins/components/

## Responsibility

Contains focused Gradle project configuration components used by `CorePlugin`.

## Design Patterns

Uses small internal `Project` extension functions for plugin application, Android options, lint, Spotless, dependency registration, source artifacts, Maven publication, and version property reading. Configuration branches by module type instead of duplicating build scripts.

## Data & Control Flow

`configurePlugins` applies Android, Kotlin, Maven Publish, Dokka, and Spotless as appropriate. `configureAndroid` sets SDK, Java 21, Kotlin JVM target, source sets, packaging, tests, lint, and sample app defaults. `configureDependencies` adds local jars and delegates catalog dependencies to `DependencyStrategy`. `configureSources` registers source jars, optional core classes jar, and defers Maven publication until after evaluation.

## Integration Points

Uses Gradle `Project`, Android application and library extensions, Kotlin compile tasks, JUnit Platform test tasks, Spotless, Maven Publish, version catalog aliases, and `gradle/version.properties`.
