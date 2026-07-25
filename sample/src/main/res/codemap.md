# sample/src/main/res/

## Responsibility

Android production resources for the sample app. It contains layouts, navigation graph definitions, and default value resources used by the activity and fragments.

## Design Patterns

Uses XML resource separation by resource type. Layout XML defines view structure, navigation XML declares fragment destinations, and values XML centralizes strings, dimensions, and themes.

## Data & Control Flow

Resources are compiled into `R` identifiers. `MainActivity` and fragments reference those identifiers at runtime for view inflation, toolbar setup, button listeners, snackbar text, and navigation actions.

## Integration Points

Integrates with Android resource merging, AppCompat and Material themes, AndroidX Navigation, ConstraintLayout, and Kotlin code under the sample package.
