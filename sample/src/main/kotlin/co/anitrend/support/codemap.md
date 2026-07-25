# sample/src/main/kotlin/co/anitrend/support/

## Responsibility

Support package segment for the sample app. It is an intermediate namespace between the organization prefix and the query builder sample implementation.

## Design Patterns

Namespace aggregation pattern. Concrete design choices are implemented below `query.builder.sample`.

## Data & Control Flow

Runtime flow is delegated to child packages. This folder only contributes package organization.

## Integration Points

Maintains the package prefix shared with the support query builder library APIs imported by the sample.
