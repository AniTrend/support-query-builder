# core/src/main/kotlin/co/anitrend/support/query/

## Responsibility

Contains the query-oriented package branch for support query building.

## Design Patterns

Keeps query builder concerns below `builder`, with no direct implementation at this namespace level.

## Data & Control Flow

Control proceeds into `builder.dsl` for fluent mutation or `builder.core` for direct clause and builder usage.

## Integration Points

Serves as the common package prefix for JVM core APIs consumed by Room integration and sample code.
