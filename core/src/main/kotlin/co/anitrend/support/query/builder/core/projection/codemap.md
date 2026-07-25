# core/src/main/kotlin/co/anitrend/support/query/builder/core/projection/

## Responsibility

Models SELECT, GROUP BY, ORDER BY, and predicate expressions that can render SQL and expose bind parameters.

## Design Patterns

`Projection` is a sealed class with column, aggregate, alias, constant, and subquery variants. Constants become placeholders or NULL, aggregates wrap columns, and aliases keep their underlying projection available for contexts that must remove aliases.

## Data & Control Flow

Projection values are added to builder collections or embedded in criteria and orders. Rendering emits SQL expressions, while constants and subqueries contribute bind arguments through `buildParameters()`.

## Integration Points

Used across `QueryBuilder`, `Criteria`, `Order`, from join helpers, projection extensions, and DSL selection or grouping functions.
