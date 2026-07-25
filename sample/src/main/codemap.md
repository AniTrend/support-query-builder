# sample/src/main/

## Responsibility

Production Android source set for the sample app. It contains the launcher manifest, Kotlin code for UI and Room data access, and resources for layouts, navigation, theme, dimensions, and strings.

## Design Patterns

Uses a conventional Android split between manifest, Kotlin packages, and resources. Runtime behavior is centered on a single activity, two navigation fragments, Room entities, DAOs, and query builder adapters.

## Data & Control Flow

The launcher activity loads the main layout and toolbar, fragments navigate through the navigation graph, and Room data code demonstrates creating raw queries from generated entity schemas.

## Integration Points

Integrates Android framework startup through `AndroidManifest.xml`, AndroidX navigation through `res/navigation`, view inflation through `res/layout`, and generated KSP schema classes through Kotlin query builders.
