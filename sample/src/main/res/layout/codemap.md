# sample/src/main/res/layout/

## Responsibility

XML view layouts for the sample app. They define the activity shell, content container, and two fragment screens used by the navigation graph.

## Design Patterns

Uses a CoordinatorLayout activity shell with AppBar, toolbar, included content, and a Material floating action button. Fragment layouts use ConstraintLayout with text and navigation buttons.

## Data & Control Flow

`MainActivity` inflates `activity_main`, which includes `content_main`. `FirstFragment` and `SecondFragment` inflate their respective layouts, then read button IDs to register navigation listeners.

## Integration Points

References values resources for dimensions and strings, Material component widgets, AppCompat toolbar styling, and controller classes through `tools:context` metadata.
