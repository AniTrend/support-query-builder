# core/src/main/kotlin/co/anitrend/support/query/builder/core/criteria/extensions/

## Responsibility

Provides fluent Kotlin extension functions for constructing `Criteria` predicates from projections, strings, values, and subqueries.

## Design Patterns

Uses infix extensions for SQL-like readability, including `and`, `or`, equality, comparisons, match, like, range, membership, and exists helpers. String receivers are converted to columns before building predicates.

## Data & Control Flow

Extensions allocate `Criteria` value objects without rendering immediately. Rendering and bind extraction happen later when a parent builder or join clause calls the criteria object.

## Integration Points

Imported by the DSL package, join helpers, and direct consumers that need predicate construction outside the high-level DSL.
