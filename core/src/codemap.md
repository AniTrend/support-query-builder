# core/src/

## Responsibility

Contains source sets for the JVM core query builder module. In production scope, `main` contains the public builder API and SQL clause implementations.

## Design Patterns

Keeps production code under the Kotlin source set and separates module build configuration at `core/build.gradle.kts`. The source tree preserves package ownership by clause type and DSL layer.

## Data & Control Flow

Production flow starts in DSL entrypoints or direct `QueryBuilder` usage, then delegates rendering and parameter collection to clause objects under `core` packages.

## Integration Points

Feeds the module artifact built by the shared Gradle plugin. Tests, generated output, and Android extension code are outside this subtree.
