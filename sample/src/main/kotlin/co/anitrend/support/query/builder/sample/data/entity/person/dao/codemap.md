# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/person/dao/

## Responsibility

Room DAO boundary for person data access. `PersonDao` exposes a simple row count and a raw query endpoint returning `PersonEntity` rows.

## Design Patterns

Uses Room DAO interface declarations with one static SQL `@Query` and one dynamic `@RawQuery`. Dynamic query construction is kept outside the DAO.

## Data & Control Flow

Room implements the interface at compile time. `count()` executes fixed SQL, while `rawQuery(query)` accepts a `SupportSQLiteQuery` produced by `PersonQueryBuilder` and maps rows to entities.

## Integration Points

Provided by `Store.person()`, consumed by `PersonQueryBuilder`, and backed by Room runtime and `androidx.sqlite.db.SupportSQLiteQuery`.
