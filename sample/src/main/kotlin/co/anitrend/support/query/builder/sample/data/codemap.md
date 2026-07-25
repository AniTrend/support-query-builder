# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/

## Responsibility

Room-backed data layer for the sample app. It contains database construction plus entity, DAO, and query builder packages for people and pets.

## Design Patterns

Uses Room as the persistence boundary and small adapter classes to translate filter state into support query builder DSL calls. Entity definitions are annotated for both Room and query schema generation.

## Data & Control Flow

`Store.create()` builds an in-memory Room database, seeds person and pet rows on creation, exposes DAOs, and query builder classes transform optional filter fields into raw Room queries.

## Integration Points

Integrates with Android context, Room runtime, Android SQLite APIs, the annotations module, the processor-generated schema objects, and `:core:ext` for `SupportSQLiteQuery` conversion.
