# core/ext/src/main/kotlin/co/anitrend/support/query/builder/core/ext/

## Responsibility

Owns the concrete Android Room bridge API. `QueryBuilderExtension.kt` provides `AbstractQueryBuilder.asSupportSQLiteQuery()`, converting core builder output into a `SupportSQLiteQuery`.

## Design Patterns

Implements a small adapter through a Kotlin extension function. It preserves the core builder type, avoids subclassing, and wraps generated SQL plus bind arguments in AndroidX `SimpleSQLiteQuery`.

## Data & Control Flow

A caller invokes `asSupportSQLiteQuery()` on an `AbstractQueryBuilder`. The function calls `build()` for SQL text, calls `buildParameters()` for bind values, converts parameters to a typed array, and constructs `SimpleSQLiteQuery` for Room or AndroidX SQLite consumers.

## Integration Points

Depends on `co.anitrend.support.query.builder.core.contract.AbstractQueryBuilder` from `:core` and AndroidX SQLite `SimpleSQLiteQuery` plus `SupportSQLiteQuery`. The returned query is suitable for Room raw query DAO methods.
