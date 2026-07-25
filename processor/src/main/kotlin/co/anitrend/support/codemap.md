# processor/src/main/kotlin/co/anitrend/support/

## Responsibility

Support library namespace for the query builder processor. It groups compile-time support code below the shared AniTrend support package family.

## Design Patterns

Namespace bridge between organization ownership and the query builder package. Processing responsibilities are intentionally placed deeper in the hierarchy.

## Data & Control Flow

This layer does not transform data. Compiler data flows through nested query builder processor packages.

## Integration Points

Aligns processor package structure with the rest of the support query builder modules.
