# SQLite and Android Query Map

Use this map to answer SQL and Android SQLite integration questions in support-query-builder.

## Module Ownership

| Concern | Module | Source of truth |
| --- | --- | --- |
| SQL select/where/group/order/union rendering | `:core` | `core/src/main/kotlin/co/anitrend/support/query/builder/core/QueryBuilder.kt` |
| Clause abstractions and builder contracts | `:core` | `core/src/main/kotlin/co/anitrend/support/query/builder/core/contract/AbstractQueryBuilder.kt` |
| DSL entrypoints and infix helpers | `:core` | `core/src/main/kotlin/co/anitrend/support/query/builder/dsl/QueryBuilderDsl.kt` |
| Translate builder to Room raw query type | `:core:ext` | `core/ext/src/main/kotlin/co/anitrend/support/query/builder/core/ext/QueryBuilderExtension.kt` |
| Runtime demo and Room DAO raw query call sites | `:sample` | `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/*` |

## Runtime Flow

1. Build SQL string and argument list in `QueryBuilder.build()` and `QueryBuilder.buildParameters()`.
2. Call `asSupportSQLiteQuery()` to create `SimpleSQLiteQuery(build(), buildParameters().toTypedArray())`.
3. Pass resulting `SupportSQLiteQuery` to Room DAO `@RawQuery` methods.

## Capability Checks For New SQLite Functions

Before adding or expanding SQL function usage in query builders:

1. Confirm the function is available in Android SQLite runtimes targeted by this project.
2. Validate behavior on minSdk and latest Android API levels.
3. Capture runtime signals when diagnosing compatibility:
	- `SELECT sqlite_version();`
	- `PRAGMA compile_options;`
4. Favor function usage that degrades safely if unavailable, or gate advanced usage behind explicit checks.
5. Keep Android docs and SQLite upstream docs in sync during design review, with Android compatibility as the deciding constraint.

## Package Index

- `co.anitrend.support.query.builder.core`
- `co.anitrend.support.query.builder.core.contract`
- `co.anitrend.support.query.builder.core.contract.query`
- `co.anitrend.support.query.builder.core.criteria`
- `co.anitrend.support.query.builder.core.criteria.extensions`
- `co.anitrend.support.query.builder.core.from`
- `co.anitrend.support.query.builder.core.from.extentions`
- `co.anitrend.support.query.builder.core.order`
- `co.anitrend.support.query.builder.core.order.extensions`
- `co.anitrend.support.query.builder.core.projection`
- `co.anitrend.support.query.builder.core.projection.extensions`
- `co.anitrend.support.query.builder.dsl`
- `co.anitrend.support.query.builder.core.ext`

## SQLite and Android References

- SQLite docs index: https://sqlite.org/docs.html
- Android SQLite package summary: https://developer.android.com/reference/android/database/sqlite/package-summary
