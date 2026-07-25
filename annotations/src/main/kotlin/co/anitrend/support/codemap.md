# annotations/src/main/kotlin/co/anitrend/support/

## Responsibility

Groups support library package content for the annotations module. It leads to the query builder annotation API namespace.

## Design Patterns

Uses a layered package namespace to separate support query builder APIs from other possible AniTrend packages. This folder is a structural namespace only.

## Data & Control Flow

Compilation passes through this namespace to resolve nested source declarations. No executable logic or data transformation is defined at this level.

## Integration Points

Connects the organization namespace to the query builder package path used by the annotation artifact and compiler consumers.
