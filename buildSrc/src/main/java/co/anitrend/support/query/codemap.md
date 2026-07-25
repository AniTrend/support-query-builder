# buildSrc/src/main/java/co/anitrend/support/query/

## Responsibility

Query domain namespace for the buildSrc convention plugin.

## Design Patterns

Acts as a package grouping layer that narrows ownership to query builder build tooling. Concrete Gradle logic is located in nested packages.

## Data & Control Flow

Gradle plugin loading crosses this namespace before reaching the `builder.buildSrc` implementation packages.

## Integration Points

Provides package continuity between repository modules and the convention plugin used to configure them.
