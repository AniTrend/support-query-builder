# core/src/main/kotlin/co/anitrend/support/query/builder/core/contract/

## Responsibility

Defines the abstract query builder contract used by the concrete builder and DSL extensions.

## Design Patterns

`AbstractQueryBuilder` is a template base with mutable clause state, pagination flags, distinct state, union state, and protected clause rendering hooks. It implements the public `IQueryBuilder` contract but leaves SQL assembly to subclasses.

## Data & Control Flow

DSL calls mutate properties on `AbstractQueryBuilder`. `QueryBuilder` reads those properties and invokes clause-specific hooks to emit SQL and collect bind values.

## Integration Points

Imported by `QueryBuilder`, DSL functions, from extensions, and consumers that operate on the abstract builder type.
