# buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/extension/

## Responsibility

Provides Gradle helper extensions used by buildSrc plugin components.

## Design Patterns

Uses Kotlin extension functions and properties as a thin adapter layer over Gradle APIs. Module predicates centralize project classification, typed extension accessors avoid repeated `extensions.getByType` calls, and dependency helpers map semantic function names to Gradle configuration names.

## Data & Control Flow

Plugin components call project predicates to choose sample, Android library, Kotlin JVM, core, ext, annotations, and processor behavior. Components then retrieve typed Gradle extensions and add dependencies through helper functions that delegate to `DependencyHandler.add`.

## Integration Points

Integrates with `Modules`, `PropertiesReader`, Android Gradle Plugin extensions, Spotless, Maven Publish, Kotlin JVM, version catalogs, and Gradle dependency configurations.
