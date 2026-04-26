# Annotation Processing Map

Use this map to understand how room metadata becomes generated schema constants.

## Current State

- Processor type: KSP `SymbolProcessor`
- Processing entrypoint: `Provider` -> `Processor`
- Codegen library: KotlinPoet
- Output writer: KSP `CodeGenerator`

## Pipeline

1. Consumer annotates Room entity with `@EntitySchema`.
2. `Provider` creates `Processor` for the current symbol-processing environment.
3. `Processor` finds annotated symbols in `process(...)` and forwards valid `KSClassDeclaration` instances.
4. `Candidate` extracts table name, `@ColumnInfo` columns, and `@Embedded` prefixes.
5. `ClassFactory` builds KotlinPoet `TypeSpec` and `FileSpec`.
6. KotlinPoet output is written through the KSP `CodeGenerator`.

## Core Files

| Step | File |
| --- | --- |
| Annotation API | `annotations/src/main/kotlin/co/anitrend/support/query/builder/annotation/EntitySchema.kt` |
| Processor entrypoint | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/Provider.kt` |
| Processor implementation | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/Processor.kt` |
| Candidate creation helpers | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/extensions/CodeAnalyserExtension.kt` |
| Metadata model | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/Candidate.kt` |
| KotlinPoet output commit | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/factory/ClassFactory.kt` |
| Column mapping | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/column/ColumnItem.kt` |
| Embedded mapping | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/embed/EmbedItem.kt` |
| Table mapping | `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/table/TableItem.kt` |

## Build Wiring

- Processor module applies the KSP plugin and depends on `:annotations`.
- Sample module consumes processor with `ksp(project(":processor"))`.
- The processor test harness uses the Zac Sweers kotlin-compile-testing fork with KSP enabled.

## Migration Notes

The KAPT-to-KSP migration is complete in current source:

1. `Provider` and `Processor` replace the old KAPT entrypoint shape.
2. Symbol traversal is KSP-based through `KSClassDeclaration` and related symbols.
3. Generated Kotlin is emitted through `CodeGenerator` plus KotlinPoet.
4. Module and sample wiring use `ksp(...)`.
5. Validate schema stability with `./gradlew :processor:test` and `./gradlew :sample:kspDebugKotlin`.

## External References

- KSP overview: https://kotlinlang.org/docs/ksp-overview.html
- Android KSP migration: https://developer.android.com/build/migrate-to-ksp
- KotlinPoet: https://square.github.io/kotlinpoet/
