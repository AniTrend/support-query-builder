---
name: jenv-gradle-low-ram
description: 'Align jenv with .java-version and run Gradle reliably on low-RAM machines. Use for Java mismatch errors, Gradle OOMs, daemon memory pressure, and selecting safe build flags.'
argument-hint: 'Describe your target task and available RAM, for example: assembleDebug on 8GB RAM'
---
# Jenv + Gradle Low-RAM Workflow

## What This Skill Produces

- A shell where `java` and Gradle use the Java version pinned by `.java-version`.
- A repeatable Gradle command profile tuned for constrained memory.
- Verification checks to confirm version alignment and build stability.

## When To Use

- Any time when `./gradlew` is about to be invoked in this repository.
- Especially important for low-RAM machines where daemon/worker pressure can destabilize builds.
- Required when verifying that Java from `.java-version` is the active runtime for Gradle.

## Procedure

1. Detect the required Java version.

```bash
cat .java-version
```

The expected output is `21.0.8`. Systems in this project are expected to have `jenv` installed; the `.java-version` file is read by `jenv local` to pin the active JDK for the working directory.

2. Verify `jenv` is installed and initialized.

```bash
command -v jenv
jenv versions
jenv version
```

Decision point:

- If `jenv` is not found, install it from https://github.com/jenv/jenv and run `eval "$(jenv init -)"` in your shell startup, then re-open the shell and retry.
- If `jenv` is found but `jenv version` does not match `.java-version`, continue to step 3.

3. Align local Java to the repository pin.

```bash
required_java="$(cat .java-version)"
jenv local "$required_java"
```

Decision point:

- If `jenv local` fails because the version is missing, install that JDK and run `jenv add <jdk-path>`, then retry.

4. Confirm runtime alignment before any Gradle task.

```bash
java -version
./gradlew -version
```

Quality check:

- Gradle JVM version in `./gradlew -version` must match `.java-version` major version (21).

5. Run Gradle with low-memory-safe defaults.

Preferred one-off profile for building a debug variant:

```bash
./gradlew --no-daemon --max-workers=2 \
  -Dorg.gradle.jvmargs="-Xmx1536m -XX:MaxMetaspaceSize=512m -Dfile.encoding=UTF-8" \
  :core:assembleDebug
```

For running unit tests:

```bash
./gradlew --no-daemon --max-workers=2 \
  -Dorg.gradle.jvmargs="-Xmx1792m -XX:MaxMetaspaceSize=512m -Dfile.encoding=UTF-8" \
  :core:test :processor:test
```

For running all tests across modules:

```bash
./gradlew --no-daemon --max-workers=2 \
  -Dorg.gradle.jvmargs="-Xmx1792m -XX:MaxMetaspaceSize=512m -Dfile.encoding=UTF-8" \
  test
```

6. If memory pressure continues, downgrade concurrency.

```bash
./gradlew --no-daemon --max-workers=1 -Dorg.gradle.parallel=false :core:test
```

7. If builds are stable and you want persistence, prefer user-local Gradle overrides (avoid committing repo-wide memory changes).

Suggested entries for `~/.gradle/gradle.properties`:

```properties
org.gradle.daemon=false
org.gradle.parallel=false
org.gradle.workers.max=2
org.gradle.jvmargs=-Xmx1536m -XX:MaxMetaspaceSize=512m -Dfile.encoding=UTF-8
```

## Troubleshooting Branches

- `jenv version` correct but `java -version` wrong:
  Ensure shell init runs `eval "$(jenv init -)"` and enable export plugin with `jenv enable-plugin export`.
- `Gradle daemon disappeared unexpectedly`:
  Retry with `--no-daemon --max-workers=1` and lower `-Xmx` if the OS is reclaiming memory aggressively.
- Kotlin compile OOM:
  Keep workers low, disable parallel, and run module-targeted tasks (for example `:core:test` or `:processor:test`) instead of full-project builds.
- KAPT out of memory (`:processor` module):
  Add `-Dkapt.use.worker.api=false` to avoid spawning an extra worker process during annotation processing.

## Completion Checks

- `.java-version` and `jenv version` resolve to the same Java release (`21.0.8`).
- `./gradlew -version` reports the expected JVM (major version 21).
- Target Gradle task completes without OOM or daemon crash.
- Machine remains responsive during build (no prolonged swap thrash).

## Fast Invocation Examples

- "Align my shell to `.java-version` and run debug build with 8GB RAM settings"
- "I get class file major version errors; fix jenv and verify Gradle JVM"
- "Run unit tests with a low-memory Gradle profile and fallback if OOM occurs"
- "Run only the processor module tests without spawning extra KAPT workers"
