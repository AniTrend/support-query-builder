# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/pet/dao/

## Responsibility

Room DAO boundary for pet data access. `PetDao` exposes a row count and a raw query endpoint returning `PetEntity` rows.

## Design Patterns

Uses a Room DAO interface with fixed SQL for counting and `@RawQuery` for dynamic SQL. Query composition remains in the query package.

## Data & Control Flow

Room generates the DAO implementation. `count()` executes static SQL, and `rawQuery(query)` executes a `SupportSQLiteQuery` built by `PetQueryBuilder` and maps results to pet entities.

## Integration Points

Provided by `Store.pet()`, consumed by `PetQueryBuilder`, and backed by Room runtime and `androidx.sqlite.db.SupportSQLiteQuery`.
