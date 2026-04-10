---
description: Use when editing Gradle files, module dependencies, version catalog entries, GitHub workflows, or buildSrc logic in support-query-builder.
applyTo: build.gradle.kts, settings.gradle.kts, gradle/**/*.toml, buildSrc/**/*.kt, */build.gradle.kts, .github/workflows/*.yml
---

# Build Logic Guidance

- Prefer the shared `co.anitrend.support.query.builder.plugin` plugin and `buildSrc` helpers over duplicating Android, Kotlin, Spotless, publishing, or test configuration in individual modules.
- The pinned Java and Kotlin toolchain is 21. Keep new build logic compatible with `.java-version` (set to `21.0.8`) and the shared Android configuration. Systems are expected to have `jenv` installed; the `.java-version` file is what `jenv local` reads to align the active JDK.
- Add or update dependency versions in `gradle/libs.versions.toml` first, then reference the alias from modules or build logic.
- Keep module dependency changes aligned with the existing graph: `:core` and `:annotations` are JVM-only with no project dependencies; `:core:ext` depends only on `:core`; `:processor` depends only on `:annotations`.
- `:annotations` and `:core` and `:processor` are classified as Kotlin library group (JVM-only, no Android plugin). `:core:ext` is an Android library. `:sample` is an Android application excluded from CI.
- Shared Android defaults come from `buildSrc/.../ProjectConfiguration.kt`, including `compileSdk = 35`, `minSdk = 23`, `targetSdk = 35`, Java 21 compatibility, and `JvmTarget.JVM_21`.
- Shared formatting comes from `buildSrc/.../ProjectSpotless.kt` and the license header file under `spotless/copyright.kt`.
- Shared publishing behavior comes from `buildSrc/.../ProjectMaven.kt`; all published artifacts use the group `co.anitrend.query.builder` and are distributed via JitPack.
- If you need a new convention across many modules, prefer adding it once in `buildSrc` instead of repeating it in each `build.gradle.kts` file.
- When validating Gradle changes locally, pair the work with the existing `jenv-gradle-low-ram` skill if JDK alignment or memory pressure becomes a problem.
