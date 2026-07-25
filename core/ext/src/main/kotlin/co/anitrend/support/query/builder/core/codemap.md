# core/ext/src/main/kotlin/co/anitrend/support/query/builder/core/

## Responsibility

Core bridge namespace for Android specific extensions to the query builder core package. It contains the nested extension package that owns the actual conversion API.

## Design Patterns

Uses package adjacency to place Android bridge behavior beside core builder contracts without modifying the core module. The concrete implementation follows an extension adapter design.

## Data & Control Flow

Compilation proceeds to the nested `ext` package. At runtime, `AbstractQueryBuilder` instances are adapted by descendant code into AndroidX SQLite query objects.

## Integration Points

Connects the `:core` query contracts to Android Room compatible query types through a separate Android library dependency.
