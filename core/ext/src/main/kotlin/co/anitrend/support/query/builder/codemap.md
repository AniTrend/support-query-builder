# core/ext/src/main/kotlin/co/anitrend/support/query/builder/

## Responsibility

Query builder namespace for the Android extension module. It narrows ownership to APIs that extend the core query builder for Android consumers.

## Design Patterns

Preserves the core package prefix so extension imports are discoverable near the base builder types. The Android specific adapter remains in the nested `core.ext` package.

## Data & Control Flow

Build control compiles descendant Kotlin sources into the bridge artifact. Runtime flow starts from core builder instances and reaches AndroidX SQLite wrapping in the nested extension package.

## Integration Points

Integrates with the published core builder namespace while keeping AndroidX SQLite dependencies scoped to the extension module.
