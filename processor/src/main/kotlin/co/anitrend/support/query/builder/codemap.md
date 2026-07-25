# processor/src/main/kotlin/co/anitrend/support/query/builder/

## Responsibility

Query builder namespace for processor production code. It houses the `processor` subtree that generates schema constants for Room-backed query builder usage.

## Design Patterns

Feature namespace layer. The concrete processor implementation is isolated beneath `processor`, keeping compile-time generation concerns separate from runtime core query builder packages.

## Data & Control Flow

No classes are defined directly in this folder. KSP control enters descendant processor classes and produces generated schema objects in the originating entity package.

## Integration Points

Connects the processor artifact package naming to the broader support query builder library surface.
