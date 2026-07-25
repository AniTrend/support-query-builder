# core/src/main/kotlin/co/anitrend/support/query/builder/core/from/

## Responsibility

Models FROM clause sources, including tables, subqueries, joins, and partially configured joins.

## Design Patterns

`From` is a sealed class implementing the shared builder contract. `Aliasable` centralizes alias state for tables and subqueries. `Join.Partial` represents a staged join until an `on` condition finalizes it.

## Data & Control Flow

Tables and subqueries render source expressions, optional aliases are appended during build, joins compose left and right source fragments with join type and criteria. Subquery and join parameters are delegated to nested builders and criteria.

## Integration Points

Used by `QueryBuilder` for FROM rendering, by DSL `from` and join functions, and by projection helpers that qualify columns with table names.
