# buildSrc/src/main/java/co/

## Responsibility

Top-level package namespace for the repository's buildSrc plugin code.

## Design Patterns

Acts as a namespace boundary only. Concrete build logic is delegated to nested `anitrend` packages.

## Data & Control Flow

No direct control flow exists at this level. Gradle execution reaches child packages through the plugin implementation class registered by the marker resource.

## Integration Points

Maintains the package prefix used by the plugin implementation class path and internal helper packages.
