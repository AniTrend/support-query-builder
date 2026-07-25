# sample/src/main/kotlin/

## Responsibility

Kotlin production source root for the sample app. It hosts the app package hierarchy for UI controllers, Room database setup, Room entities, DAOs, and query builder wrappers.

## Design Patterns

Uses package-based layering rather than feature modules. UI classes remain in the app package, while data classes are grouped under `data` with database, entity, DAO, and query subpackages.

## Data & Control Flow

Control starts in `MainActivity`, moves through fragments for navigation, and reaches Room data access through query builder classes that convert fluent SQL definitions into `SupportSQLiteQuery` objects.

## Integration Points

Consumes AndroidX, Material, Room, generated schema constants from the processor, and `:core:ext` conversion helpers from the library under demonstration.
