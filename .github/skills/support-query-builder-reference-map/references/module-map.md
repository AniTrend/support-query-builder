# Module Reference Map

Use this map to determine where code belongs before searching for a specific file.

| Module | Depends on | Package roots | Use for | Dokka |
| --- | --- | --- | --- | --- |
| `:annotations` | none | `co.anitrend.support.query.builder.annotation` | Public annotation definitions such as `@EntitySchema` | `https://anitrend.github.io/support-query-builder/annotations/index.html` |
| `:core` | none | `contract/`, `criteria/`, `from/`, `order/`, `projection/`, `dsl/` | The entire fluent query builder API — `QueryBuilder`, `AbstractQueryBuilder`, `IQueryBuilder`, `Criteria`, `From`, `Order`, `Projection`, DSL helpers | `https://anitrend.github.io/support-query-builder/core/index.html` |
| `:core:ext` | `:core` | `co.anitrend.support.query.builder.core.ext` | Room/SQLite integration: `asSupportSQLiteQuery()` extension and parameter binding helpers | `https://anitrend.github.io/support-query-builder/core/ext/index.html` |
| `:processor` | `:annotations` | `extensions/`, `factory/`, `logger/`, `model/` | KAPT annotation processor (`EntitySchemaProcessor`) that reads `@Entity`, `@ColumnInfo`, `@Embedded` and generates schema object classes via KotlinPoet | `https://anitrend.github.io/support-query-builder/processor/index.html` |
| `:sample` | all modules | `co.anitrend.support.query.builder.sample` | Local development and integration examples only; excluded from CI | — |

## Placement Heuristics

- Pure query builder logic with no Android dependency: `:core`.
- Room, SQLite, or Android-specific integration: `:core:ext`.
- New annotation visible to consumers: `:annotations`.
- Annotation processor logic, code generation, or KotlinPoet usage: `:processor`. Keep annotation API changes coordinated with processor changes.
- Usage examples or integration tests that require an Android runtime: `:sample`.

## Package Detail

- `:core` — `contract/query/IQueryBuilder.kt` defines the query interface; `contract/AbstractQueryBuilder.kt` is the base implementation; `criteria/`, `from/`, `order/`, `projection/` each provide clause-specific builders; `dsl/QueryBuilderDsl.kt` and extension files provide Kotlin DSL entry points.
- `:processor` — `EntitySchemaProcessor.kt` is the KAPT entry point; `model/` contains `Candidate`, `ColumnItem`, `EmbedItem`, `FieldItem`, `TableItem`; `factory/ClassFactory.kt` drives KotlinPoet code generation; `logger/` provides a logging abstraction.

## Consumer Notes

- Consumers add `:core` for the query builder, `:core:ext` for Room `@RawQuery` integration, `:annotations` + `:processor` (as `kapt`) for compile-time schema generation.
- `:annotations` and `:core` are JVM-only and can be used in non-Android Kotlin projects.
- `:core:ext` is an Android library; add it only when Room integration is needed.
- If a change affects a public type, assume the Dokka page is part of the deliverable.
