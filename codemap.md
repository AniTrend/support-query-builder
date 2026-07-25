# Repository Atlas: support-query-builder

## Project Responsibility

Kotlin and Android library repository for fluent, type-safe SQL query building with ordered bind parameters and Room raw-query compatibility. The repository combines a platform-neutral core DSL, an Android SQLite adapter, a source-retained annotation API, a KSP schema processor, shared Gradle convention logic, and a local sample app that demonstrates generated schema constants flowing into Room `@RawQuery` execution.

## System Entry Points

- `settings.gradle.kts`: Declares the `:core`, `:core:ext`, `:annotations`, and `:processor` modules, with `:sample` included outside CI.
- `build.gradle.kts`: Configures root repositories, Dokka publication output, and module source links for published API documentation.
- `buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/plugins/CorePlugin.kt`: Shared Gradle convention plugin applied by library and sample modules.
- `annotations/src/main/kotlin/co/anitrend/support/query/builder/annotation/EntitySchema.kt`: Public marker annotation consumed by the KSP processor.
- `core/src/main/kotlin/co/anitrend/support/query/builder/core/QueryBuilder.kt`: SQL assembly coordinator for clauses and bind parameters.
- `core/src/main/kotlin/co/anitrend/support/query/builder/dsl/QueryBuilderDsl.kt`: Kotlin DSL entrypoint for constructing query builders.
- `core/ext/src/main/kotlin/co/anitrend/support/query/builder/core/ext/QueryBuilderExtension.kt`: AndroidX SQLite adapter that creates `SupportSQLiteQuery` values.
- `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/Provider.kt`: KSP provider entrypoint for schema generation.
- `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/Processor.kt`: KSP processing loop that discovers annotated entity declarations.
- `sample/src/main/AndroidManifest.xml`: Sample app Android entrypoint for local integration verification.

## Root Assets

- `gradle/libs.versions.toml`: Central version catalog for Gradle plugins, AndroidX, KSP, KotlinPoet, Room, Dokka, Spotless, and test dependencies.
- `gradle/version.properties`: Publication metadata consumed by buildSrc convention logic.
- `gradlew` and `gradlew.bat`: Gradle wrapper launchers for local and CI builds.
- `gradle/wrapper/`: Gradle wrapper distribution metadata.
- `AGENTS.md`: Repository guidance for agents, including module ownership, package index, and startup workflow.
- `.slim/codemap.json`: Codemap state file used for hash-based change detection.

## Runtime Flow

1. Consumer code calls the DSL in `:core` to mutate an `AbstractQueryBuilder`.
2. Clause objects render SQL fragments through `build()` and expose ordered bind values through `buildParameters()`.
3. `QueryBuilder` assembles SELECT, FROM, WHERE, GROUP BY, UNION, ORDER BY, LIMIT, and OFFSET clauses in SQL order.
4. Android callers pass the builder to `asSupportSQLiteQuery()` in `:core:ext`.
5. The adapter wraps SQL text and bind parameters in `SimpleSQLiteQuery` for Room DAO raw-query methods.

## Annotation Processing Flow

1. Consumer source marks a Room entity class with `@EntitySchema` from `:annotations`.
2. KSP loads `Provider`, which creates `Processor`.
3. `Processor.process` scans annotated class declarations and passes valid symbols to the code generation pipeline.
4. Candidate extraction reads Room `@Entity`, `@ColumnInfo`, and `@Embedded` metadata.
5. KotlinPoet builders emit `<EntityName>Schema` objects containing table and column constants.
6. Sample query builders use generated schema constants to create typed SQL filters that execute through Room.

## Module Dependency Map

- `:annotations`: No project dependencies, provides the annotation contract.
- `:core`: No project dependencies, provides SQL builder contracts and DSL implementation.
- `:core:ext`: Depends on `:core`, adapts builders to AndroidX SQLite query types.
- `:processor`: Depends on `:annotations`, reads Room metadata with KSP and emits KotlinPoet output.
- `:sample`: Depends on all local modules, demonstrates processor output and Room raw-query integration.
- `buildSrc`: Builds the shared convention plugin that configures the modules above.

## Directory Map, Module Level

| Directory | Responsibility Summary | Detailed Map |
|-----------|------------------------|--------------|
| `buildSrc/` | Build logic module that compiles the repository's Gradle convention plugin. It centralizes Android, Kotlin, publishing, formatting, and dependency defaults used by project modules. | [View Map](buildSrc/codemap.md) |
| `annotations/` | Defines the public annotation API for the query builder toolchain. The module currently exposes the `EntitySchema` marker annotation and configures the annotations artifact for Kotlin compilation and JAR metadata. | [View Map](annotations/codemap.md) |
| `core/` | Owns the platform-neutral JVM SQL query builder module. It exposes the core clause model, mutable builder implementation, and Kotlin DSL used to assemble SQL strings plus ordered bind parameters. | [View Map](core/codemap.md) |
| `core/ext/` | Android bridge module for adapting core query builder output into AndroidX SQLite query objects. The module owns Android namespace configuration, module dependencies, and the production source set that exposes the Room compatible API. | [View Map](core/ext/codemap.md) |
| `processor/` | Compile-time KSP processor module that turns Room entity metadata annotated with `@EntitySchema` into Kotlin schema objects. The module owns processor registration, symbol scanning, Room annotation extraction, KotlinPoet model assembly, and generated file emission. | [View Map](processor/codemap.md) |
| `sample/` | Android sample app module that demonstrates Room raw-query use with the query builder library. It owns app packaging, KSP wiring, Android UI dependencies, and the production sample source under `src/main`. | [View Map](sample/codemap.md) |
| `gradle/` | Central Gradle metadata directory for dependency version alignment, wrapper distribution configuration, and publication version properties. It supports all project modules through shared dependency aliases and repository release metadata. | [View Map](gradle/codemap.md) |

## Directory Map, Full Sub-Map Index

| Directory | Responsibility Summary | Detailed Map |
|-----------|------------------------|--------------|
| `annotations/src/` | Contains the source sets for the annotations module. The included production source is under `main`, which holds the annotation API shipped by the module. | [View Map](annotations/src/codemap.md) |
| `annotations/src/main/` | Hosts production sources for the annotations artifact. The current content is the Kotlin package tree that defines the entity schema marker annotation. | [View Map](annotations/src/main/codemap.md) |
| `annotations/src/main/kotlin/co/anitrend/` | Owns the AniTrend package namespace for annotation sources. It scopes the query builder annotation API under the organization package. | [View Map](annotations/src/main/kotlin/co/anitrend/codemap.md) |
| `annotations/src/main/kotlin/co/anitrend/support/` | Groups support library package content for the annotations module. It leads to the query builder annotation API namespace. | [View Map](annotations/src/main/kotlin/co/anitrend/support/codemap.md) |
| `annotations/src/main/kotlin/co/anitrend/support/query/builder/annotation/` | Defines `EntitySchema`, the source-retained class annotation that marks an entity for schema object generation. | [View Map](annotations/src/main/kotlin/co/anitrend/support/query/builder/annotation/codemap.md) |
| `annotations/src/main/kotlin/co/anitrend/support/query/builder/` | Scopes query builder public APIs owned by the annotations module. Its active child package contains the schema generation marker annotation. | [View Map](annotations/src/main/kotlin/co/anitrend/support/query/builder/codemap.md) |
| `annotations/src/main/kotlin/co/anitrend/support/query/` | Scopes query-related support APIs for the annotations module. It routes the package tree toward the query builder annotation contract. | [View Map](annotations/src/main/kotlin/co/anitrend/support/query/codemap.md) |
| `annotations/src/main/kotlin/co/` | Begins the Kotlin package namespace for the annotations module. It groups all production annotation API packages under the `co` root. | [View Map](annotations/src/main/kotlin/co/codemap.md) |
| `annotations/src/main/kotlin/` | Contains the Kotlin production package hierarchy for the annotations module. It roots the `co.anitrend.support.query.builder.annotation` namespace. | [View Map](annotations/src/main/kotlin/codemap.md) |
| `buildSrc/src/` | Source root for the buildSrc convention plugin. It separates plugin implementation and resources from Gradle build configuration. | [View Map](buildSrc/src/codemap.md) |
| `buildSrc/src/main/` | Production source set for the repository convention plugin. It contains the plugin implementation package and Gradle plugin marker resources. | [View Map](buildSrc/src/main/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/` | Organization namespace for Anitrend build logic inside buildSrc. | [View Map](buildSrc/src/main/java/co/anitrend/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/` | Support library namespace segment for buildSrc tooling. | [View Map](buildSrc/src/main/java/co/anitrend/support/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/` | Implementation root for the repository's Gradle convention plugin. It owns module identity, Gradle extension helpers, plugin orchestration, component configuration, and dependency selection. | [View Map](buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/extension/` | Provides Gradle helper extensions used by buildSrc plugin components. | [View Map](buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/extension/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/module/` | Defines canonical Gradle module identifiers used by the convention plugin. | [View Map](buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/module/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/plugins/` | Owns the Gradle convention plugin entrypoint and plugin-level orchestration. | [View Map](buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/plugins/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/plugins/components/` | Contains focused Gradle project configuration components used by `CorePlugin`. | [View Map](buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/plugins/components/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/plugins/strategy/` | Encapsulates dependency selection rules for modules configured by the convention plugin. | [View Map](buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/plugins/strategy/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/query/builder/` | Query builder namespace for all buildSrc convention plugin implementation. | [View Map](buildSrc/src/main/java/co/anitrend/support/query/builder/codemap.md) |
| `buildSrc/src/main/java/co/anitrend/support/query/` | Query domain namespace for the buildSrc convention plugin. | [View Map](buildSrc/src/main/java/co/anitrend/support/query/codemap.md) |
| `buildSrc/src/main/java/co/` | Top-level package namespace for the repository's buildSrc plugin code. | [View Map](buildSrc/src/main/java/co/codemap.md) |
| `buildSrc/src/main/java/` | Kotlin source tree for the custom Gradle convention plugin. The directory name follows Gradle's Java source set convention while storing Kotlin files. | [View Map](buildSrc/src/main/java/codemap.md) |
| `core/ext/src/` | Source set container for the Android bridge module. The included production source lives under `main`, with no bridge logic owned directly at this level. | [View Map](core/ext/src/codemap.md) |
| `core/ext/src/main/` | Production source root for the Android Room bridge. It contains the Kotlin namespace tree that exposes the `AbstractQueryBuilder` to `SupportSQLiteQuery` conversion API. | [View Map](core/ext/src/main/codemap.md) |
| `core/ext/src/main/kotlin/co/anitrend/` | Organization namespace segment for production bridge code. It contains the support query builder package branch used by the Android extension API. | [View Map](core/ext/src/main/kotlin/co/anitrend/codemap.md) |
| `core/ext/src/main/kotlin/co/anitrend/support/` | Support namespace segment for the Android bridge module. It groups query builder extension code beneath the shared support package hierarchy. | [View Map](core/ext/src/main/kotlin/co/anitrend/support/codemap.md) |
| `core/ext/src/main/kotlin/co/anitrend/support/query/builder/` | Query builder namespace for the Android extension module. It narrows ownership to APIs that extend the core query builder for Android consumers. | [View Map](core/ext/src/main/kotlin/co/anitrend/support/query/builder/codemap.md) |
| `core/ext/src/main/kotlin/co/anitrend/support/query/builder/core/` | Core bridge namespace for Android specific extensions to the query builder core package. It contains the nested extension package that owns the actual conversion API. | [View Map](core/ext/src/main/kotlin/co/anitrend/support/query/builder/core/codemap.md) |
| `core/ext/src/main/kotlin/co/anitrend/support/query/builder/core/ext/` | Owns the concrete Android Room bridge API. `QueryBuilderExtension.kt` provides `AbstractQueryBuilder.asSupportSQLiteQuery()`, converting core builder output into a `SupportSQLiteQuery`. | [View Map](core/ext/src/main/kotlin/co/anitrend/support/query/builder/core/ext/codemap.md) |
| `core/ext/src/main/kotlin/co/anitrend/support/query/` | Query namespace segment for Android bridge code. It leads to the builder package that adapts core query builders for Android SQLite usage. | [View Map](core/ext/src/main/kotlin/co/anitrend/support/query/codemap.md) |
| `core/ext/src/main/kotlin/co/` | Top level Kotlin package segment for the bridge module. It scopes the extension API under the `co.anitrend` namespace. | [View Map](core/ext/src/main/kotlin/co/codemap.md) |
| `core/ext/src/main/kotlin/` | Kotlin production source root for the Android extension module. It contains the package hierarchy for the Room bridge API. | [View Map](core/ext/src/main/kotlin/codemap.md) |
| `core/src/` | Contains source sets for the JVM core query builder module. In production scope, `main` contains the public builder API and SQL clause implementations. | [View Map](core/src/codemap.md) |
| `core/src/main/` | Houses production Kotlin sources for the core JVM query builder. It contains the public API surface and internal helpers that compile into the core library artifact. | [View Map](core/src/main/codemap.md) |
| `core/src/main/kotlin/co/anitrend/` | Owns the AniTrend namespace for the core query builder module. | [View Map](core/src/main/kotlin/co/anitrend/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/` | Groups support-library production code for the core SQL builder. | [View Map](core/src/main/kotlin/co/anitrend/support/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/` | Defines the root package for query builder implementation and DSL entrypoints. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/` | Contains the concrete SQL query builder and all core clause families. `QueryBuilder` assembles SQL text and bind parameters from projections, sources, criteria, grouping, unions, ordering, and pagination state. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/contract/` | Defines the abstract query builder contract used by the concrete builder and DSL extensions. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/contract/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/contract/query/` | Defines the minimal rendering contract shared by query builders and SQL clause nodes. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/contract/query/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/criteria/` | Models WHERE clause predicates and predicate composition for the core query builder. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/criteria/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/criteria/extensions/` | Provides fluent Kotlin extension functions for constructing `Criteria` predicates from projections, strings, values, and subqueries. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/criteria/extensions/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/from/` | Models FROM clause sources, including tables, subqueries, joins, and partially configured joins. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/from/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/from/extentions/` | Provides fluent construction helpers for FROM sources, aliases, and join chains. The package name is spelled `extentions` in source. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/from/extentions/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/order/` | Models ORDER BY entries for ascending and descending sort directions. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/order/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/order/extensions/` | Provides constructors for ascending and descending `Order` values from projections and column-name strings. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/order/extensions/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/projection/` | Models SELECT, GROUP BY, ORDER BY, and predicate expressions that can render SQL and expose bind parameters. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/projection/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/core/projection/extensions/` | Provides fluent factory and transformation helpers for projection values. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/core/projection/extensions/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/builder/dsl/` | Exposes the high-level Kotlin DSL for mutating an `AbstractQueryBuilder` into a complete SELECT query. | [View Map](core/src/main/kotlin/co/anitrend/support/query/builder/dsl/codemap.md) |
| `core/src/main/kotlin/co/anitrend/support/query/` | Contains the query-oriented package branch for support query building. | [View Map](core/src/main/kotlin/co/anitrend/support/query/codemap.md) |
| `core/src/main/kotlin/co/` | Namespace anchor for all production Kotlin code in the core module. | [View Map](core/src/main/kotlin/co/codemap.md) |
| `core/src/main/kotlin/` | Root for production Kotlin packages in the core JVM module. All SQL builder types are nested under the `co.anitrend.support.query.builder` namespace. | [View Map](core/src/main/kotlin/codemap.md) |
| `processor/src/` | Source-set container for the processor module. Production code lives under `main` and implements the KSP annotation processing path for schema generation. | [View Map](processor/src/codemap.md) |
| `processor/src/main/` | Production source root for the KSP processor implementation. It contains the Kotlin packages that register the processor, analyze annotated Room entities, model schema constants, and emit generated Kotlin source. | [View Map](processor/src/main/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/` | Organization namespace for AniTrend processor source. It scopes the support query builder KSP implementation under the project package identity. | [View Map](processor/src/main/kotlin/co/anitrend/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/` | Support library namespace for the query builder processor. It groups compile-time support code below the shared AniTrend support package family. | [View Map](processor/src/main/kotlin/co/anitrend/support/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/` | Query builder namespace for processor production code. It houses the `processor` subtree that generates schema constants for Room-backed query builder usage. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/codegen/` | Coordinates conversion of valid KSP class declarations into schema generation candidates and starts file generation for each processing batch. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/codegen/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/codegen/contract/` | Defines the callable contract for schema code generation stages that consume KSP resolver context and class declarations. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/codegen/contract/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/` | Main processor package. It registers the KSP provider and runs annotation processing for `EntitySchema` classes before delegating schema file generation to codegen and factory packages. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/extensions/` | Provides small KSP annotation lookup helpers used while extracting Room metadata from declarations and annotation arguments. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/extensions/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/factory/` | Owns final Kotlin source file construction and emission for generated schema objects. `ClassFactory` converts candidate model output into KotlinPoet file specs and writes them through KSP. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/factory/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/` | Owns the metadata model that converts KSP class declarations and Room annotations into generated schema items. `Candidate` is the main extraction unit for table, column, and embedded field metadata. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/column/` | Models a generated constant for a direct Room column. `ColumnItem` maps a Kotlin property name to the resolved database column name. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/column/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/core/` | Defines the shared `Item` contract for model elements that can write generated schema properties into a KotlinPoet type builder. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/core/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/embed/` | Models generated constants for Room embedded properties. `EmbedItem` combines an embedded property name, optional Room prefix, and the embedded type columns into prefixed schema constants. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/embed/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/field/` | Contains `FieldItem`, a small generic wrapper pairing a Java annotation processing `Element` with an annotation instance. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/field/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/table/` | Models a Room table for generated schema output. `TableItem` writes the table name constant and delegates column constant generation to its child items. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/table/codemap.md) |
| `processor/src/main/kotlin/co/anitrend/support/query/` | Query support namespace for compile-time schema generation code. It narrows ownership toward the query builder processor implementation. | [View Map](processor/src/main/kotlin/co/anitrend/support/query/codemap.md) |
| `processor/src/main/kotlin/co/` | Top-level Kotlin package namespace for production processor code under `co.anitrend`. This folder exists to preserve the published package path. | [View Map](processor/src/main/kotlin/co/codemap.md) |
| `processor/src/main/kotlin/` | Kotlin production namespace for the processor module. It hosts all processor classes, KSP extension helpers, metadata models, and KotlinPoet code generation components. | [View Map](processor/src/main/kotlin/codemap.md) |
| `sample/src/` | Source-set container for the sample app. The included production source set is `main`, which contains the manifest, Kotlin application code, and Android resources. | [View Map](sample/src/codemap.md) |
| `sample/src/main/` | Production Android source set for the sample app. It contains the launcher manifest, Kotlin code for UI and Room data access, and resources for layouts, navigation, theme, dimensions, and strings. | [View Map](sample/src/main/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/` | Organization-level package segment for sample production code. It groups the support query builder sample under the repository owner namespace. | [View Map](sample/src/main/kotlin/co/anitrend/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/` | Support package segment for the sample app. It is an intermediate namespace between the organization prefix and the query builder sample implementation. | [View Map](sample/src/main/kotlin/co/anitrend/support/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/` | Builder package segment for the sample app. It contains the concrete sample application package beneath `sample`. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/` | Main application package for the sample app. It owns `MainActivity` and groups UI controllers and Room-backed data demonstration code. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/controller/` | UI fragment controllers for the sample app navigation flow. `FirstFragment` and `SecondFragment` inflate their layouts and wire button clicks to navigation actions. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/controller/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/` | Room-backed data layer for the sample app. It contains database construction plus entity, DAO, and query builder packages for people and pets. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/database/` | Room database definition for the sample data layer. `Store` declares the person and pet tables, exposes DAOs, and provides a test-only in-memory creation helper. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/database/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/` | Entity domain package for the sample data layer. It groups the person and pet Room models with their DAOs and query builder helpers. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/person/` | Person aggregate for the sample Room schema. `PersonEntity` maps the `person` table and nested city value object, while child packages provide DAO and query helpers. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/person/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/person/dao/` | Room DAO boundary for person data access. `PersonDao` exposes a simple row count and a raw query endpoint returning `PersonEntity` rows. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/person/dao/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/person/query/` | Query adapter for person filtering. `PersonQueryBuilder` turns optional filter state into a fluent SQL query and executes it through `PersonDao`. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/person/query/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/pet/` | Pet aggregate for the sample Room schema. `PetEntity` maps the `pet` table, its owner relationship, and embedded breed columns, with child packages for DAO and query helpers. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/pet/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/pet/dao/` | Room DAO boundary for pet data access. `PetDao` exposes a row count and a raw query endpoint returning `PetEntity` rows. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/pet/dao/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/pet/query/` | Query adapter for pet filtering. `PetQueryBuilder` converts optional pet filter state into a dynamic SQL query and executes it through `PetDao`. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/pet/query/codemap.md) |
| `sample/src/main/kotlin/co/anitrend/support/query/` | Query package segment for the sample app. It organizes the sample under the same conceptual query domain as the library modules. | [View Map](sample/src/main/kotlin/co/anitrend/support/query/codemap.md) |
| `sample/src/main/kotlin/co/` | Top-level Kotlin package namespace for the sample app. It scopes all production Kotlin code under the organization package path. | [View Map](sample/src/main/kotlin/co/codemap.md) |
| `sample/src/main/kotlin/` | Kotlin production source root for the sample app. It hosts the app package hierarchy for UI controllers, Room database setup, Room entities, DAOs, and query builder wrappers. | [View Map](sample/src/main/kotlin/codemap.md) |
| `sample/src/main/res/` | Android production resources for the sample app. It contains layouts, navigation graph definitions, and default value resources used by the activity and fragments. | [View Map](sample/src/main/res/codemap.md) |
| `sample/src/main/res/layout/` | XML view layouts for the sample app. They define the activity shell, content container, and two fragment screens used by the navigation graph. | [View Map](sample/src/main/res/layout/codemap.md) |
| `sample/src/main/res/navigation/` | AndroidX Navigation graph for the sample app. It declares the two fragment destinations and actions for moving between them. | [View Map](sample/src/main/res/navigation/codemap.md) |
| `sample/src/main/res/values/` | Default scalar resources for the sample app. It defines activity and fragment strings, a floating action button margin dimension, and app theme overlays. | [View Map](sample/src/main/res/values/codemap.md) |

## Navigation Guidance

- Start with module-level maps when choosing where to make a change.
- Use deep sub-maps when editing package-local behavior, generated schema models, clause families, Gradle conventions, or sample data flows.
- For SQL builder behavior, begin at `core/codemap.md`, then follow clause package maps under `core/src/main/kotlin/co/anitrend/support/query/builder/core/`.
- For Room bridge behavior, begin at `core/ext/codemap.md`.
- For KSP schema generation, begin at `processor/codemap.md`, then inspect `processor/src/main/kotlin/co/anitrend/support/query/builder/processor/codemap.md`.
- For consumer usage examples, begin at `sample/codemap.md`.
