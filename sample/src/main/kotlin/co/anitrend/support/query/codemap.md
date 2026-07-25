# sample/src/main/kotlin/co/anitrend/support/query/

## Responsibility

Query package segment for the sample app. It organizes the sample under the same conceptual query domain as the library modules.

## Design Patterns

Namespace aggregation with no standalone runtime classes. Child packages specialize the query builder sample, UI, and Room data demonstration.

## Data & Control Flow

Runtime flow continues through child packages, especially the sample app package and its data query builders.

## Integration Points

Aligns package naming with library APIs such as `co.anitrend.support.query.builder.core` and generated schema packages.
