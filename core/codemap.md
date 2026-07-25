# core/

## Responsibility

Owns the platform-neutral JVM SQL query builder module. It exposes the core clause model, mutable builder implementation, and Kotlin DSL used to assemble SQL strings plus ordered bind parameters.

## Design Patterns

The module uses a small contract interface, an abstract mutable builder base, sealed clause hierarchies, and extension functions for fluent DSL syntax. `build.gradle.kts` applies the shared library plugin and wires module metadata to the core classes jar task.

## Data & Control Flow

Client code mutates an `AbstractQueryBuilder` through DSL calls, clause objects render themselves through `build()`, and each clause reports bind values through `buildParameters()`. `QueryBuilder` coordinates SELECT, FROM, WHERE, GROUP BY, UNION, ORDER BY, LIMIT, and OFFSET assembly in SQL order.

## Integration Points

Consumed by the Android bridge in `core/ext` and by generated or hand-written callers that need raw SQL plus bind arguments. The JVM module has no project dependency on Android APIs.
