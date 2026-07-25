# core/ext/src/main/kotlin/co/anitrend/support/

## Responsibility

Support namespace segment for the Android bridge module. It groups query builder extension code beneath the shared support package hierarchy.

## Design Patterns

Maintains module naming consistency through package nesting. It introduces no runtime abstractions and delegates behavior to the nested query builder package.

## Data & Control Flow

Compilation flows through this folder to the nested query builder sources. Runtime data flow remains the builder to AndroidX SQLite conversion implemented below this namespace.

## Integration Points

Provides package continuity with the core query builder API so imports remain predictable for consumers using both modules.
