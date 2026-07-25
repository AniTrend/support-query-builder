# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/controller/

## Responsibility

UI fragment controllers for the sample app navigation flow. `FirstFragment` and `SecondFragment` inflate their layouts and wire button clicks to navigation actions.

## Design Patterns

Uses AndroidX Fragment lifecycle methods with XML layout inflation. Navigation is declarative in the graph and invoked through `findNavController()` from button listeners.

## Data & Control Flow

Each fragment inflates its matching layout in `onCreateView`, then registers a click listener in `onViewCreated`. Button events navigate between `FirstFragment` and `SecondFragment` using action IDs from the navigation graph.

## Integration Points

Depends on `fragment_first`, `fragment_second`, navigation action IDs, generated `R` values, and AndroidX navigation fragment APIs.
