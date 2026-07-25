# core/src/main/kotlin/co/anitrend/support/query/builder/core/

## Responsibility

Contains the concrete SQL query builder and all core clause families. `QueryBuilder` assembles SQL text and bind parameters from projections, sources, criteria, grouping, unions, ordering, and pagination state.

## Design Patterns

Uses `AbstractQueryBuilder` as a template for state and clause construction hooks. Clause families are sealed class hierarchies that implement the same `IQueryBuilder` rendering contract.

## Data & Control Flow

Builder state is accumulated by direct property mutation or DSL extension calls. `build()` renders clauses in SQL order, while `buildParameters()` traverses the same logical clause order to produce bind arguments.

## Integration Points

Feeds the DSL package and the Android Room raw-query bridge. It depends only on Kotlin/JVM standard library constructs and module-local clause types.
