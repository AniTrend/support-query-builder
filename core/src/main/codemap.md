# core/src/main/

## Responsibility

Houses production Kotlin sources for the core JVM query builder. It contains the public API surface and internal helpers that compile into the core library artifact.

## Design Patterns

Uses package-level organization to separate the stateful query builder, immutable clause value types, clause extension factories, and DSL mutation helpers.

## Data & Control Flow

Callers create or receive an `AbstractQueryBuilder`, set projections, source tables, criteria, grouping, sorting, pagination, and unions, then request SQL text and bind values.

## Integration Points

Exports JVM classes for downstream modules, including Android Room raw-query integration in `core/ext`, without depending on those consumers.
