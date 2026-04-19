# Annotation Processing Map

Use this map to understand how room metadata becomes generated schema constants.

## Current State

- Processor type: KAPT `AbstractProcessor`
- Processing entrypoint: `EntitySchemaProcessor`
- Codegen library: KotlinPoet
- Output option key: `kapt.kotlin.generated`

## Pipeline

1. Consumer annotates Room entity with `@EntitySchema`.
2. `EntitySchemaProcessor` finds annotated elements in `process(...)`.
3. `createCandidate(...)` validates and adapts element metadata.
4. `Candidate` extracts table name, `@ColumnInfo` columns, and `@Embedded` prefixes.
5. `ClassFactory` builds KotlinPoet `TypeSpec` and `FileSpec`.
6. KotlinPoet output is committed to `kapt.kotlin.generated` directory.

## Core Files

| Step | File |
| --- | --- |
| Annotation API | `annotations/src/main/kotlin/co/anitrend/support/query/builder/annotation/EntitySchema.kt` |
| Processor entrypoint | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/EntitySchemaProcessor.kt` |
| Candidate creation helpers | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/extensions/ElementExtensions.kt` |
| Metadata model | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/Candidate.kt` |
| KotlinPoet output commit | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/factory/ClassFactory.kt` |
| Column mapping | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/column/ColumnItem.kt` |
| Embedded mapping | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/embed/EmbedItem.kt` |
| Table mapping | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/table/TableItem.kt` |

## Build Wiring

- Processor module applies kapt tasks and depends on `:annotations`.
- Sample module consumes processor with `kapt(project(":processor"))`.
- Shared plugin wiring applies `kotlin-kapt` to `:processor` and `:sample`.

## KAPT to KSP Migration Boundary Map

Keep this high-level migration map to avoid mixing APIs:

1. Replace `AbstractProcessor` entrypoint with KSP `SymbolProcessorProvider` plus `SymbolProcessor`.
2. Replace `javax.lang.model` element traversal with KSP symbol traversal.
3. Replace `kapt.kotlin.generated` output contract with KSP `CodeGenerator` output writes.
4. Update module and sample Gradle wiring from `kapt(...)` usage to KSP equivalents.
5. Re-run sample raw query workflows to verify generated schema names remain stable.

## External References

- KAPT: https://kotlinlang.org/docs/kapt.html
- KSP overview: https://kotlinlang.org/docs/ksp-overview.html
- Android KSP migration: https://developer.android.com/build/migrate-to-ksp
- KotlinPoet: https://square.github.io/kotlinpoet/
