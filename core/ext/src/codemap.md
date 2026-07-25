# core/ext/src/

## Responsibility

Source set container for the Android bridge module. The included production source lives under `main`, with no bridge logic owned directly at this level.

## Design Patterns

Follows the standard Gradle Android source set layout, separating production code from any future tests or generated outputs. The folder acts as a structural boundary, not a runtime abstraction.

## Data & Control Flow

Gradle discovers the `main` source set beneath this folder and compiles its Kotlin API into the Android library artifact. Runtime query conversion flow is implemented deeper in the package tree.

## Integration Points

Connected through the Android Gradle Plugin source set conventions used by `core/ext/build.gradle.kts`. It feeds the module artifact consumed by Android applications and Room integrations.
