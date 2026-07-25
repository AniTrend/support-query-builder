# core/src/main/kotlin/co/anitrend/support/query/builder/core/order/extensions/

## Responsibility

Provides constructors for ascending and descending `Order` values from projections and column-name strings.

## Design Patterns

Uses infix extension functions that convert strings to projections and preserve an explicit ignore-case flag for collation-aware sorting.

## Data & Control Flow

Extensions produce `Order.Ascending` or `Order.Descending` objects. Those objects remain inert until the parent builder renders ORDER BY and collects projection parameters.

## Integration Points

Used by the DSL package for `orderByAsc`, `orderByAscCollate`, `orderByDesc`, and `orderByDescCollate`.
