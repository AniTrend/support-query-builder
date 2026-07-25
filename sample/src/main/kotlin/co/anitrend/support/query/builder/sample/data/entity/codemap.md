# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/

## Responsibility

Entity domain package for the sample data layer. It groups the person and pet Room models with their DAOs and query builder helpers.

## Design Patterns

Uses one package per entity aggregate. Each aggregate defines the Room entity, a DAO boundary, and a query adapter that consumes generated schema constants.

## Data & Control Flow

Entities define database shape, DAOs expose count and raw query entry points, and query builder classes construct filtered `SupportSQLiteQuery` objects for DAO execution.

## Integration Points

Integrates with `Store` entity registration, Room annotations, `@EntitySchema` processing, generated schema objects, and the core query builder DSL.
