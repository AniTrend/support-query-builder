# core/src/main/kotlin/co/anitrend/support/query/builder/core/order/

## Responsibility

Models ORDER BY entries for ascending and descending sort directions.

## Design Patterns

`Order` is a sealed class with shared rendering for projection, optional `COLLATE NOCASE`, and direction. Direction-specific data classes supply `ASC` or `DESC`.

## Data & Control Flow

`QueryBuilder` iterates the order list, each `Order` renders a projection-based sort expression, and projection parameters are carried forward if the ordered expression contains bind values.

## Integration Points

Created through order extensions and DSL ordering functions, then consumed by `QueryBuilder` during ORDER BY rendering and parameter collection.
