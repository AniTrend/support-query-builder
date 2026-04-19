---
name: support-query-builder-sqlite-android-map
description: Use when mapping SQLite query behavior, Room raw query integration, annotation processing flow, or KAPT-to-KSP migration decisions in support-query-builder.
argument-hint: Describe the SQLite, Room, processor, or migration question you need to answer.
---

# Support Query Builder SQLite Android Map

## Overview

This skill provides a fast, source-backed map for how SQL is built, translated into Android SQLite query objects, and connected to Room via raw queries. It also maps current KAPT behavior, KSP migration boundaries, and KotlinPoet code-generation ownership.

Core principle: answer from module ownership and concrete file paths first, then use external docs to validate edge behavior.

## When To Use

- Onboarding an LLM or developer to this repository.
- Explaining where SQL text and SQL parameters are produced.
- Tracing `@EntitySchema` to generated schema object output.
- Confirming whether a behavior belongs to `:core`, `:core:ext`, `:annotations`, or `:processor`.
- Planning KAPT to KSP migration work without breaking module boundaries.

Do not use this skill for general Gradle dependency editing. Use `support-query-builder-build-dependencies` for that.

## Procedure

1. Start with the package and ownership map in [sqlite-android-query-map.md](./references/sqlite-android-query-map.md).
2. If the question involves generated schema constants, switch to [annotation-processing-map.md](./references/annotation-processing-map.md).
3. Validate claimed behavior against the exact owner file before answering.
4. When external behavior is unclear, consult the official references listed below and map findings back to local code.

## Quick Reference

| Question | Primary module | First file |
| --- | --- | --- |
| Where does SQL string building happen? | `:core` | `core/src/main/kotlin/co/anitrend/support/query/builder/core/QueryBuilder.kt` |
| Where does `SupportSQLiteQuery` come from? | `:core:ext` | `core/ext/src/main/kotlin/co/anitrend/support/query/builder/core/ext/QueryBuilderExtension.kt` |
| Where is `@EntitySchema` defined? | `:annotations` | `annotations/src/main/kotlin/co/anitrend/support/query/builder/annotation/EntitySchema.kt` |
| Where is annotation processing entrypoint? | `:processor` | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/EntitySchemaProcessor.kt` |
| Where is KotlinPoet emission committed? | `:processor` | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/factory/ClassFactory.kt` |
| Where is KAPT wired into sample usage? | `:sample` | `sample/build.gradle.kts` |
| How do I verify Android SQLite runtime capability? | `:sample` runtime diagnostics | `SELECT sqlite_version();` and `PRAGMA compile_options;` |

## Example

```kotlin
import co.anitrend.support.query.builder.core.QueryBuilder
import co.anitrend.support.query.builder.core.ext.asSupportSQLiteQuery
import co.anitrend.support.query.builder.dsl.from
import co.anitrend.support.query.builder.dsl.select

val builder = QueryBuilder()
builder select "*" from "person"

// Bridge from pure query builder to Android Room raw-query input type
val sqliteQuery = builder.asSupportSQLiteQuery()
```

## Common Mistakes

- Assuming `SupportSQLiteQuery` is created in `:core` instead of `:core:ext`.
- Assuming KSP support exists because build output folders contain `ksp` artifacts.
- Answering processor flow without checking `ClassFactory` for generated output options.
- Ignoring package typos that are intentional and already in use (for example `from.extentions`).
- Assuming a SQLite function from upstream docs is automatically available on all Android runtime versions.

## Rationalization Table

| Excuse | Reality |
| --- | --- |
| "KSP folders exist, so migration is done" | Folder artifacts are not build wiring truth. Confirm plugin and processor APIs in source/build files. |
| "I can infer output path without reading processor code" | Output path is explicitly tied to `kapt.kotlin.generated` in `ClassFactory`. |
| "Bridge code is obvious" | Verify owner module. This repo keeps SQL building and Android bridge in different modules. |

## Red Flags

- "I will answer from memory without file checks"
- "I only need README for processor details"
- "Build artifact folders are enough evidence for migration status"

If any red flag appears, stop and reopen the reference maps.

## External References

- SQLite official docs: https://sqlite.org/docs.html
- Android SQLite package summary: https://developer.android.com/reference/android/database/sqlite/package-summary
- KAPT overview: https://kotlinlang.org/docs/kapt.html
- KSP overview: https://kotlinlang.org/docs/ksp-overview.html
- Android migration to KSP: https://developer.android.com/build/migrate-to-ksp
- KotlinPoet docs: https://square.github.io/kotlinpoet/

## References

- [SQLite and Android query map](./references/sqlite-android-query-map.md)
- [Annotation processing map](./references/annotation-processing-map.md)
