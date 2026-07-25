# sample/src/main/kotlin/co/

## Responsibility

Top-level Kotlin package namespace for the sample app. It scopes all production Kotlin code under the organization package path.

## Design Patterns

Acts as a namespace aggregation layer with no runtime type ownership of its own. Child packages provide the concrete activity, fragment, database, entity, DAO, and query classes.

## Data & Control Flow

Data and control pass through child packages only. This folder preserves package structure for compiler and Android namespace resolution.

## Integration Points

Integrates with Kotlin package naming and the sample module namespace `co.anitrend.support.query.builder.sample`.
