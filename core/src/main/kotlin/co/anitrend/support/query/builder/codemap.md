# core/src/main/kotlin/co/anitrend/support/query/builder/

## Responsibility

Defines the root package for query builder implementation and DSL entrypoints.

## Design Patterns

Splits concerns between `core`, which owns SQL nodes and builder contracts, and `dsl`, which exposes Kotlin extension functions over those contracts.

## Data & Control Flow

Callers use DSL functions to populate builder state, or instantiate core clause types directly. The builder then pulls SQL fragments and bind parameters from those clauses.

## Integration Points

Provides the package consumed by downstream JVM and Android modules. Android-specific conversion remains outside this subtree.
