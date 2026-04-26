---
applyTo: **
description: Use when understanding support-query-builder architecture, module boundaries, consumer-facing APIs, annotation processing, Room integration, or shared Gradle/buildSrc behavior.
---

# Support Query Builder Context

- `support-query-builder` is a reusable Kotlin library, not an app. Favor reusable abstractions, extension points, and stable consumer-facing APIs over app-specific behavior.
- The library's primary purpose is to provide a fluent, type-safe SQL query builder that integrates with Android Room via generated schema objects and `SupportSQLiteQuery`.
- Treat the published Dokka site as part of the product surface: `https://anitrend.github.io/support-query-builder/`.

## Module Groups

- Core JVM modules: `:annotations`, `:core`, `:processor`
- Android integration module: `:core:ext`
- Development-only module: `:sample` (excluded from CI via `settings.gradle.kts`)

## Dependency Direction

- `:annotations` is a JVM-only API module with no project dependencies. It only defines the `@EntitySchema` annotation and is the lowest layer.
- `:core` is a pure JVM Kotlin module with no project dependencies. It owns the entire query builder API surface: `QueryBuilder`, `AbstractQueryBuilder`, `IQueryBuilder`, `Criteria`, `From`, `Order`, `Projection`, and their DSL helpers.
- `:core:ext` is an Android library that depends on `:core`. It bridges the query builder to Room's `SupportSQLiteQuery` via extension functions.
- `:processor` is a JVM KSP module that depends on `:annotations`. It reads Room entity annotations and generates schema object classes using KotlinPoet and `auto-service`.
- `:sample` depends on everything and is an Android application used for local development only.
- Avoid introducing new dependencies that cause lower modules to depend on higher-layer ones.

## Package Expectations

- `:annotations` exposes `co.anitrend.support.query.builder.annotation` — the `@EntitySchema` annotation.
- `:core` exposes `contract/`, `criteria/`, `from/`, `order/`, `projection/`, and the top-level `QueryBuilder` and `dsl/` entrypoints.
- `:core:ext` exposes `co.anitrend.support.query.builder.core.ext` — the `asSupportSQLiteQuery()` extension and related Room helpers.
- `:processor` exposes `codegen/`, `extensions/`, `factory/`, `logger/`, and `model/` under `co.anitrend.support.query.builder.processor`. `Provider` is the `SymbolProcessorProvider` entry point and `Processor` is the `SymbolProcessor` implementation.

## Build And Tooling Facts

- All modules apply the shared `co.anitrend.support.query.builder.plugin` Gradle plugin from `buildSrc`.
- Shared Android defaults live in `buildSrc/.../ProjectConfiguration.kt`: `compileSdk = 36`, `minSdk = 23`, `targetSdk = 35`, Java 21 source/target compatibility, and `JvmTarget.JVM_21` for Kotlin.
- The repo Java pin is `.java-version = 21.0.8`. Systems are expected to have `jenv` installed; `.java-version` is picked up by `jenv local` to set the active JDK automatically. `buildSrc/.../ProjectConfiguration.kt` must stay aligned: `JavaVersion.VERSION_21` for Java source/target compatibility and `JvmTarget.JVM_21` for Kotlin must both match the `.java-version` major version (21).
- Kotlin library group (JVM-only, no Android plugin): `:annotations`, `:core`, `:processor`. All other non-sample modules are Android libraries.
- Dependency versions belong in `gradle/libs.versions.toml` before they are referenced from module build files.
- Spotless and ktlint are enforced centrally via `buildSrc/.../ProjectSpotless.kt`, with the license header sourced from `spotless/copyright.kt`.
- Publishing is configured centrally in `buildSrc/.../ProjectMaven.kt`; artifacts are distributed through JitPack under group `co.anitrend.query.builder`.

## Documentation Contract

- Dokka is configured centrally and the published site documents the public API surface.
- When changing public behavior, update KDoc in the same change.
- Document what the API does, when to use it, and what a consumer must provide or expect.

## Working Heuristics

- Put new query builder logic in `:core` if it has no Android dependency; put Room-specific glue in `:core:ext`.
- Annotation additions go in `:annotations` first; corresponding processor changes go in `:processor` in the same change.
- Prefer shared build logic changes in `buildSrc` over copy-pasting Gradle configuration into individual modules.
- When running Gradle locally, use the `jenv-gradle-low-ram` skill to align the JDK and manage memory pressure before invoking `./gradlew`.
- When unsure where code belongs, consult the dependency direction above and confirm it before editing.
