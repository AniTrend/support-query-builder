# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/pet/query/

## Responsibility

Query adapter for pet filtering. `PetQueryBuilder` converts optional pet filter state into a dynamic SQL query and executes it through `PetDao`.

## Design Patterns

Uses a builder adapter pattern around the core `QueryBuilder`. The nested `Query` data class represents nullable filter values, and non-null values append `AND` predicates.

## Data & Control Flow

`filter()` sets the generated pet table name, appends predicates for id, name, owner, breed group, and breed origin, converts the builder to `SupportSQLiteQuery`, then delegates to `dao.rawQuery()`.

## Integration Points

Depends on `:core` criteria and DSL extensions, `:core:ext.asSupportSQLiteQuery`, generated `PetEntitySchema`, `PetEntity`, and `PetDao`.
