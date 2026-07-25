# core/src/main/kotlin/co/anitrend/support/

## Responsibility

Groups support-library production code for the core SQL builder.

## Design Patterns

Uses namespace layering only at this level. Concrete builder abstractions live below `query.builder`.

## Data & Control Flow

No direct data transformation occurs here. Clause construction and rendering occur in nested packages.

## Integration Points

Maintains a stable package prefix for consumers and generated schema code that reference the query builder API.
