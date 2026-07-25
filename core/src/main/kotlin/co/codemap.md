# core/src/main/kotlin/co/

## Responsibility

Namespace anchor for all production Kotlin code in the core module.

## Design Patterns

Delegates all implementation to the `anitrend` namespace, keeping the package root free of behavior.

## Data & Control Flow

No direct runtime flow exists at this level. Query construction flow continues in nested packages.

## Integration Points

Stabilizes the package path used by compiled JVM consumers and downstream modules.
