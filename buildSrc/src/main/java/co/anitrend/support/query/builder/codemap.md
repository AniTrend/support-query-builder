# buildSrc/src/main/java/co/anitrend/support/query/builder/

## Responsibility

Query builder namespace for all buildSrc convention plugin implementation.

## Design Patterns

Separates build logic into the `buildSrc` child package instead of mixing it with runtime library packages. This keeps Gradle automation clearly scoped to build tooling.

## Data & Control Flow

Plugin control flow enters the `buildSrc.plugins.CorePlugin` child package, which then delegates to sibling extension, module, component, and strategy packages.

## Integration Points

Bridges the package namespace used by the support query builder modules with the convention plugin applied by their Gradle scripts.
