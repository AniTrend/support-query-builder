# sample/src/main/kotlin/co/anitrend/support/query/builder/

## Responsibility

Builder package segment for the sample app. It contains the concrete sample application package beneath `sample`.

## Design Patterns

Package boundary that mirrors the library namespace and separates sample code from the reusable query builder modules.

## Data & Control Flow

Control and data flow are implemented by the `sample` child package, including activity startup, fragment navigation, Room setup, and query execution.

## Integration Points

Provides namespace continuity for imports from `:core`, `:core:ext`, and schema classes generated into the sample package.
