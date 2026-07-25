# buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/plugins/strategy/

## Responsibility

Encapsulates dependency selection rules for modules configured by the convention plugin.

## Design Patterns

Uses a strategy class bound to a Gradle `Project`. Private dependency groups define default Kotlin and test dependencies separately from sample-only Android lifecycle dependencies.

## Data & Control Flow

`configureDependencies` creates `DependencyStrategy(project)` and calls `applyDependenciesOn`. The strategy adds Kotlin stdlib, reflection, JUnit, MockK, and Kotlin test for all modules, then adds lifecycle dependencies when the target project is the sample module.

## Integration Points

Consumes `Project.libs` version catalog accessors and custom `DependencyHandler` extension functions. Uses `isSampleModule` from project extensions for branching.
