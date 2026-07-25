# annotations/src/main/kotlin/

## Responsibility

Contains the Kotlin production package hierarchy for the annotations module. It roots the `co.anitrend.support.query.builder.annotation` namespace.

## Design Patterns

Uses package nesting to mirror the public namespace and keep the annotation API discoverable. The tree contains only source definitions, not generated or test code.

## Data & Control Flow

Kotlin compilation resolves files in this tree and produces the annotation class for compile-time use. The only metadata flow is from the source marker into KSP-visible symbols.

## Integration Points

Feeds the annotations module artifact through the Gradle Kotlin compiler task. Public package names are consumed by processor code and by entity classes that import the annotation.
