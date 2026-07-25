# processor/src/main/kotlin/co/anitrend/support/query/

## Responsibility

Query support namespace for compile-time schema generation code. It narrows ownership toward the query builder processor implementation.

## Design Patterns

Namespace layer with no direct source definitions. Specific builder and processor responsibilities are split into descendant packages.

## Data & Control Flow

No processing logic executes directly here. KSP symbol data continues into `builder.processor` classes.

## Integration Points

Keeps processor packages aligned with query builder module naming and generated schema package conventions.
