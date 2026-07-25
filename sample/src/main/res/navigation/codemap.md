# sample/src/main/res/navigation/

## Responsibility

AndroidX Navigation graph for the sample app. It declares the two fragment destinations and actions for moving between them.

## Design Patterns

Uses declarative navigation XML with stable destination IDs and action IDs. The first fragment is the start destination.

## Data & Control Flow

The navigation host reads `nav_graph`, starts at `FirstFragment`, and uses action IDs invoked by fragment button listeners to switch between first and second fragment destinations.

## Integration Points

Connects `FirstFragment` and `SecondFragment` classes to layout previews, string labels, generated `R.id` action constants, and AndroidX navigation runtime.
