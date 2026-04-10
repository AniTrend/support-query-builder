# Build Map

Use this map to choose the right build file before editing.

| Concern | Primary files | Notes |
| --- | --- | --- |
| Module includes | `settings.gradle.kts` | Declares every published module; `:sample` is excluded when `CI` env var is set |
| Root repositories and classpath | `build.gradle.kts` | Sets up Google, MavenCentral, JitPack, and AGP/Kotlin classpath |
| Shared plugin entry point | `buildSrc/.../plugins/CorePlugin.kt` | Applies Android or Kotlin plugin, Dokka, Maven publishing, Spotless, sources, and dependency strategy |
| Shared plugin application | `buildSrc/.../plugins/components/ProjectPlugins.kt` | Decides Android application vs library vs Kotlin JVM based on module identity |
| Shared Android/JVM defaults | `buildSrc/.../plugins/components/ProjectConfiguration.kt` | `compileSdk = 35`, `minSdk = 23`, `targetSdk = 35`, Java 21, `JvmTarget.JVM_21` |
| Shared dependency strategy | `buildSrc/.../plugins/strategy/DependencyStrategy.kt` | Default Kotlin stdlib, Kotlin reflect, JUnit 4, MockK; lifecycle libs only for `:sample` |
| Shared formatting | `buildSrc/.../plugins/components/ProjectSpotless.kt`, `spotless/copyright.kt` | Ktlint and license header configuration |
| Shared publishing | `buildSrc/.../plugins/components/ProjectMaven.kt` | Maven publication under group `co.anitrend.query.builder`, distributed via JitPack |
| Shared lint | `buildSrc/.../plugins/components/ProjectLint.kt` | Lint configuration applied to non-sample modules |
| Shared sources | `buildSrc/.../plugins/components/ProjectSources.kt` | Sources JAR configuration |
| Module identity helpers | `buildSrc/.../extension/ProjectExtensions.kt` | `isSampleModule()`, `isAnnotationModule()`, `isCoreModule()`, `isKotlinLibraryGroup()`, etc. |
| Dependency versions and aliases | `gradle/libs.versions.toml` | Add or update aliases here first |
| Annotation API | `annotations/build.gradle.kts`, `annotations/src/main/...` | JVM-only public annotation module, no project dependencies |
| KAPT processor wiring | `processor/build.gradle.kts`, `processor/src/main/...` | Depends on `:annotations`; uses KotlinPoet, `auto-service`, and Room compiler headers |
| Room/SQLite extension | `core/ext/build.gradle.kts` | Android library; depends on `:core` and `androidx.sqlite:sqlite-ktx` |

## Module Dependency Snapshot

- `:annotations` — JVM-only, no project dependencies.
- `:core` — JVM-only, no project dependencies. Owns the entire query builder API.
- `:core:ext` — Android library. Depends on `:core`. Adds Room/SQLite integration.
- `:processor` — JVM KAPT module. Depends on `:annotations`. Uses KotlinPoet and `auto-service`.
- `:sample` — Android application. Excluded from CI. Depends on all library modules.

## Kotlin Library Group

Modules classified as JVM-only (Kotlin plugin, no Android plugin): `:annotations`, `:core`, `:processor`.
All other non-sample modules use the Android library plugin.

## Edit Strategy

- New library version or alias: `gradle/libs.versions.toml`.
- Cross-module convention: `buildSrc`.
- One module only: that module's `build.gradle.kts`.
- Annotation surface change: update both `:annotations` and `:processor` when needed.
- JDK version: `.java-version` contains `21.0.8` (read by `jenv`). `ProjectConfiguration.kt` must use `JavaVersion.VERSION_21` for source/target compatibility and `JvmTarget.JVM_21` for Kotlin — both must match the `.java-version` major version (21).
