# core/src/main/kotlin/co/anitrend/support/query/builder/core/projection/extensions/

## Responsibility

Provides fluent factory and transformation helpers for projection values.

## Design Patterns

Uses extension functions to convert strings to columns, values to constants, builders to subquery projections, columns to aggregate projections, and columns to aliases. An internal helper strips aliases for criteria and grouping contexts.

## Data & Control Flow

Extensions create immutable projection nodes or unwrap aliases. Actual SQL rendering and parameter extraction are deferred until a parent query or clause builds itself.

## Integration Points

Imported by the DSL, criteria, order, from, and query builder implementations to normalize column and expression construction.
