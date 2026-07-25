# buildSrc/src/main/java/

## Responsibility

Kotlin source tree for the custom Gradle convention plugin. The directory name follows Gradle's Java source set convention while storing Kotlin files.

## Design Patterns

Organized by the `co.anitrend.support.query.builder.buildSrc` package. The source tree favors small Gradle `Project` extension functions and a single plugin entrypoint over large build scripts.

## Data & Control Flow

Compiled classes provide module classification, extension lookup, dependency helpers, plugin application, Android configuration, publishing setup, and dependency defaults. `CorePlugin.apply` is the control entrypoint.

## Integration Points

Integrates with Gradle APIs and typed extension APIs from Android, Kotlin, Spotless, Maven Publish, and version catalog accessors.
