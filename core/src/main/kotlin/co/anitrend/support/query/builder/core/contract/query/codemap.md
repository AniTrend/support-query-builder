# core/src/main/kotlin/co/anitrend/support/query/builder/core/contract/query/

## Responsibility

Defines the minimal rendering contract shared by query builders and SQL clause nodes.

## Design Patterns

`IQueryBuilder` uses a small interface with `build()` for SQL text and `buildParameters()` for ordered bind values. This keeps builders, subqueries, and clauses composable.

## Data & Control Flow

Parent nodes call child `build()` methods to create nested SQL fragments and call child `buildParameters()` methods to append bind arguments in traversal order.

## Integration Points

Implemented by `AbstractQueryBuilder`, `QueryBuilder`, and sealed clause types such as `Projection`, `Criteria`, `From`, and `Order`.
