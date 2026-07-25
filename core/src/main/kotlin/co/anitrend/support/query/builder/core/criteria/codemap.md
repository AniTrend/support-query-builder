# core/src/main/kotlin/co/anitrend/support/query/builder/core/criteria/

## Responsibility

Models WHERE clause predicates and predicate composition for the core query builder.

## Design Patterns

`Criteria` is a sealed class with data class variants for logical composition, comparison operators, ranges, membership, EXISTS checks, and value-between-column checks. It removes projection aliases before criteria rendering where SQL requires raw expressions.

## Data & Control Flow

Each predicate renders its SQL fragment with placeholders when binding literal values. Parameter collection combines projection parameters, subquery parameters, and predicate literals in the same nested order used for rendering.

## Integration Points

Used by `QueryBuilder` for WHERE clauses, by `From.Join` for ON clauses, and by criteria extension functions for fluent predicate construction.
