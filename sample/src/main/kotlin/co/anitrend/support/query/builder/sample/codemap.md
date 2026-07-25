# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/

## Responsibility

Main application package for the sample app. It owns `MainActivity` and groups UI controllers and Room-backed data demonstration code.

## Design Patterns

Uses a single-activity Android pattern with XML layouts and navigation fragments. Data responsibilities are delegated to a `data` package with Room entities, DAOs, and query builder adapters.

## Data & Control Flow

`MainActivity` inflates `activity_main`, installs the toolbar, and handles the floating action button snackbar. Navigation fragments are hosted by the layout graph, while data sample flows are available through Room database and query classes.

## Integration Points

References generated `R` resources, AndroidX AppCompat, Material components, fragment navigation resources, Room entities, and query builder library extensions.
