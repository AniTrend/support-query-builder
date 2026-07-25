# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/person/query/

## Responsibility

Query adapter for person filtering. `PersonQueryBuilder` turns optional filter state into a fluent SQL query and executes it through `PersonDao`.

## Design Patterns

Uses a builder adapter pattern around `QueryBuilder`. The nested `Query` data class models UI-like filter state with nullable fields, and each present field appends an `AND` criterion.

## Data & Control Flow

`filter()` starts a `QueryBuilder`, sets the generated `PersonEntitySchema.tableName`, adds equality or match predicates for non-null fields, converts to `SupportSQLiteQuery`, then calls `dao.rawQuery()`.

## Integration Points

Depends on `:core` DSL functions, `:core:ext.asSupportSQLiteQuery`, generated `PersonEntitySchema`, `PersonEntity`, and `PersonDao`.
