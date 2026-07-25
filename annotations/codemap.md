# annotations/

## Responsibility

Defines the public annotation API for the query builder toolchain. The module currently exposes the `EntitySchema` marker annotation and configures the annotations artifact for Kotlin compilation and JAR metadata.

## Design Patterns

Uses a declarative marker annotation with source retention so consumers can opt classes into schema generation without adding runtime annotation overhead. The module is intentionally small and independent, which keeps the processor dependency boundary narrow.

## Data & Control Flow

During compilation, Kotlin source files can mark entity classes with `@EntitySchema`. The annotation exists only in source for compiler and KSP analysis, then the processor reads it and emits schema code outside this module. The Gradle file applies the shared convention plugin, adds Kotlin compiler flags, and writes artifact identity into the JAR manifest.

## Integration Points

Consumed by the processor module as the annotation contract for candidate discovery. Consumed by application or library source that wants generated schema objects. Built through the repository convention plugin declared as `co.anitrend.support.query.builder.plugin`.
