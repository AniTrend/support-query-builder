# core/ext/src/main/kotlin/

## Responsibility

Kotlin production source root for the Android extension module. It contains the package hierarchy for the Room bridge API.

## Design Patterns

Uses Kotlin package organization to mirror the published namespace. The implementation relies on Kotlin extension functions so Android specific behavior can be added without changing core classes.

## Data & Control Flow

Compilation descends from this root to the concrete package that defines the extension function. Runtime control starts when callers import that function and invoke it on an `AbstractQueryBuilder` instance.

## Integration Points

Integrated with Gradle Kotlin compilation for the Android library. The source references core contracts and AndroidX SQLite APIs declared in the module Gradle dependencies.
