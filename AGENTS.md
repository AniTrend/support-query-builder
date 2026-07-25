# AGENTS

Repository-level orientation for coding agents working in support-query-builder.

## Repository Identity

- Type: Kotlin library repository with Android integration module and sample app.
- Primary goal: fluent, type-safe SQL query building with Room raw-query compatibility.
- Default branch: `develop`.

## Module Graph

- `:annotations` (JVM-only): defines annotation API, currently `@EntitySchema`.
- `:core` (JVM-only): query builder contracts, SQL clause types, and DSL entrypoints.
- `:core:ext` (Android library): translates builder output into `SupportSQLiteQuery`.
- `:processor` (JVM-only): KSP processor that reads Room metadata and emits schema objects via KotlinPoet.
- `:sample` (Android app, local development): demonstrates end-to-end usage; excluded from CI.

Dependency direction:

- `:annotations` has no project dependencies.
- `:core` has no project dependencies.
- `:core:ext` depends on `:core`.
- `:processor` depends on `:annotations`.
- `:sample` depends on all library modules.

## Package Index

### annotations

- `co.anitrend.support.query.builder.annotation`

### core

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

### core ext

- `co.anitrend.support.query.builder.core.ext`

### processor

- `co.anitrend.support.query.builder.processor`
- `co.anitrend.support.query.builder.processor.extensions`
- `co.anitrend.support.query.builder.processor.factory`
- `co.anitrend.support.query.builder.processor.logger`
- `co.anitrend.support.query.builder.processor.logger.contract`
- `co.anitrend.support.query.builder.processor.model`
- `co.anitrend.support.query.builder.processor.model.column`
- `co.anitrend.support.query.builder.processor.model.core`
- `co.anitrend.support.query.builder.processor.model.embed`
- `co.anitrend.support.query.builder.processor.model.field`
- `co.anitrend.support.query.builder.processor.model.table`

## Critical Ownership Map

- SQL builder implementation:
  - `core/src/main/kotlin/co/anitrend/support/query/builder/core/QueryBuilder.kt`
  - `core/src/main/kotlin/co/anitrend/support/query/builder/core/contract/AbstractQueryBuilder.kt`
  - `core/src/main/kotlin/co/anitrend/support/query/builder/core/contract/query/IQueryBuilder.kt`
  - `core/src/main/kotlin/co/anitrend/support/query/builder/dsl/QueryBuilderDsl.kt`

- Android Room raw-query bridge:
  - `core/ext/src/main/kotlin/co/anitrend/support/query/builder/core/ext/QueryBuilderExtension.kt`

- Annotation and processing pipeline:
  - `annotations/src/main/kotlin/co/anitrend/support/query/builder/annotation/EntitySchema.kt`
  - `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/Provider.kt`
  - `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/Processor.kt`
  - `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/extensions/CodeAnalyserExtension.kt`
  - `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/Candidate.kt`
  - `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/factory/ClassFactory.kt`

## Runtime Query Flow

1. Build SQL string and bind parameters in `QueryBuilder`.
2. Convert query to `SupportSQLiteQuery` with `asSupportSQLiteQuery()` in `:core:ext`.
3. Pass query to Room DAO `@RawQuery` method in consuming app or sample.

## Android SQLite Capability Guardrails

When expanding SQL features or adding new SQLite functions, validate against Android runtime SQLite capabilities instead of desktop SQLite assumptions.

1. Treat SQLite feature support as runtime-dependent on Android devices and API levels.
2. Verify capability-sensitive SQL on representative API levels (at least minSdk and latest).
3. Use runtime checks when needed:
  - `SELECT sqlite_version();`
  - `PRAGMA compile_options;`
4. Prefer conservative SQL for shared paths used by `:core` and consumed through Room raw queries.
5. Document any minimum-runtime expectations when introducing advanced SQL functions.

This repository should align with Android SQLite behavior first, then broader upstream SQLite documentation.

## Annotation Processing Flow

1. Entity class is marked with `@EntitySchema`.
2. `Provider` creates `Processor`, which scans annotations using the KSP `SymbolProcessor` API.
3. Candidate extraction reads Room `@Entity`, `@ColumnInfo`, `@Embedded` metadata.
4. KotlinPoet writes `<EntityName>Schema` object constants.
5. Output is written through the KSP `CodeGenerator` using KotlinPoet.

## KSP and KotlinPoet Notes

- Current processor implementation is KSP-first (`SymbolProcessorProvider`, `SymbolProcessor`, `CodeGenerator`).
- Consumer wiring uses the KSP Gradle plugin and `ksp(project(":processor"))` in the sample module.
- KotlinPoet is the code-generation backbone for emitted schema objects.

## External Documentation Anchors

- SQLite docs index: https://sqlite.org/docs.html
- Android SQLite package summary: https://developer.android.com/reference/android/database/sqlite/package-summary
- KSP overview: https://kotlinlang.org/docs/ksp-overview.html
- KSP migration guide: https://developer.android.com/build/migrate-to-ksp
- KotlinPoet docs: https://square.github.io/kotlinpoet/

## Existing Skill Entry Points

- Module placement and package ownership: `.github/skills/support-query-builder-reference-map/SKILL.md`
- Build and dependency wiring: `.github/skills/support-query-builder-build-dependencies/SKILL.md`
- KDoc and Dokka expectations: `.github/skills/support-query-builder-kdoc-dokka/SKILL.md`
- SQLite, Android, and processor flow map: `.github/skills/support-query-builder-sqlite-android-map/SKILL.md`

## Suggested Agent Startup Sequence

1. Read this file first.
2. Open `settings.gradle.kts` for module graph confirmation.
3. If task is SQLite or raw query related, use the SQLite map skill.
4. If task changes build wiring, use build dependencies skill.
5. If task changes public API docs, use kdoc-dokka skill.

## Repository Map

A full codemap is available at `codemap.md` in the project root.

Before working on any task, read `codemap.md` to understand:
- Project architecture and entry points
- Directory responsibilities and design patterns
- Data flow and integration points between modules

For deep work on a specific folder, also read that folder's `codemap.md`.

<!-- code-review-graph MCP tools -->
## MCP Tools: code-review-graph

**IMPORTANT: This project has a knowledge graph. ALWAYS use the
code-review-graph MCP tools BEFORE using Grep/Glob/Read to explore
the codebase.** The graph is faster, cheaper (fewer tokens), and gives
you structural context (callers, dependents, test coverage) that file
scanning cannot.

### When to use graph tools FIRST

- **Exploring code**: `semantic_search_nodes` or `query_graph` instead of Grep
- **Understanding impact**: `get_impact_radius` instead of manually tracing imports
- **Code review**: `detect_changes` + `get_review_context` instead of reading entire files
- **Finding relationships**: `query_graph` with callers_of/callees_of/imports_of/tests_for
- **Architecture questions**: `get_architecture_overview` + `list_communities`

Fall back to Grep/Glob/Read **only** when the graph doesn't cover what you need.

### Key Tools

| Tool | Use when |
|------|----------|
| `detect_changes` | Reviewing code changes — gives risk-scored analysis |
| `get_review_context` | Need source snippets for review — token-efficient |
| `get_impact_radius` | Understanding blast radius of a change |
| `get_affected_flows` | Finding which execution paths are impacted |
| `query_graph` | Tracing callers, callees, imports, tests, dependencies |
| `semantic_search_nodes` | Finding functions/classes by name or keyword |
| `get_architecture_overview` | Understanding high-level codebase structure |
| `refactor_tool` | Planning renames, finding dead code |

### Workflow

1. The graph auto-updates on file changes (via hooks).
2. Use `detect_changes` for code review.
3. Use `get_affected_flows` to understand impact.
4. Use `query_graph` pattern="tests_for" to check coverage.
