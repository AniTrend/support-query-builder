# sample/src/main/res/values/

## Responsibility

Default scalar resources for the sample app. It defines activity and fragment strings, a floating action button margin dimension, and app theme overlays.

## Design Patterns

Uses Android values XML to centralize reusable constants and styles. Themes inherit from Material3 DayNight and toolbar overlay themes.

## Data & Control Flow

Values are compiled into resources and resolved by manifest labels, navigation labels, layout text, layout margins, and activity theme references at runtime.

## Integration Points

Integrates with `AndroidManifest.xml`, layout XML, navigation XML, Material theme parents, and generated `R.string`, `R.dimen`, and `R.style` identifiers. Locale-specific translations are not present in this folder.
