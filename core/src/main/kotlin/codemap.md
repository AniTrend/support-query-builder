# core/src/main/kotlin/

## Responsibility

Root for production Kotlin packages in the core JVM module. All SQL builder types are nested under the `co.anitrend.support.query.builder` namespace.

## Design Patterns

Uses Kotlin sealed classes for clause variants, data classes for simple SQL nodes, and extension functions for readable query construction.

## Data & Control Flow

Control flows from DSL extensions into mutable builder state. Data flows from projections, sources, criteria, ordering, and subqueries into SQL fragments and parameter lists.

## Integration Points

Provides package namespaces consumed by the Android extension module, sample app, and any JVM consumer that needs query strings and bind arguments.
