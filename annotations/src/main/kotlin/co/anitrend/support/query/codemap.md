# annotations/src/main/kotlin/co/anitrend/support/query/

## Responsibility

Scopes query-related support APIs for the annotations module. It routes the package tree toward the query builder annotation contract.

## Design Patterns

Maintains a domain-oriented namespace split between query support and the builder-specific API below it. The folder contains no direct declarations.

## Data & Control Flow

There is no runtime flow at this namespace level. Build-time symbol resolution continues into the builder annotation package where the marker annotation is declared.

## Integration Points

Bridges the broader support namespace to the query builder packages imported by processor and consumer source code.
