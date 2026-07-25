# core/ext/src/main/kotlin/co/

## Responsibility

Top level Kotlin package segment for the bridge module. It scopes the extension API under the `co.anitrend` namespace.

## Design Patterns

Acts as a namespace segment in the package hierarchy. No behavior is implemented here, which keeps ownership concentrated in the deeper `core.ext` package.

## Data & Control Flow

Build tooling traverses this folder to compile the nested Kotlin package. Runtime flow bypasses this folder as a structural package segment and enters the concrete extension function below it.

## Integration Points

Aligns the Android bridge package path with the repository namespace and module namespace declared in Gradle.
