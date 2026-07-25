# sample/

## Responsibility

Android sample app module that demonstrates Room raw-query use with the query builder library. It owns app packaging, KSP wiring, Android UI dependencies, and the production sample source under `src/main`.

## Design Patterns

Uses the repository Android convention plugin, KSP annotation processing, and a simple layered sample structure. The module depends on `:annotations`, `:core`, `:core:ext`, and `:processor` to exercise schema generation and Room query execution.

## Data & Control Flow

Gradle applies Android and KSP setup, compiles annotated Room entities, runs the processor to generate schema objects, then packages the UI, database, DAO, and query builder sample code into the app.

## Integration Points

Integrates with Room runtime and compiler, AndroidX navigation and fragments, Material components, and the local query builder modules. Android test dependencies are declared but production codemap scope is `src/main`.
