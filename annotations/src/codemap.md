# annotations/src/

## Responsibility

Contains the source sets for the annotations module. The included production source is under `main`, which holds the annotation API shipped by the module.

## Design Patterns

Follows the standard Gradle source-set layout. Production API is isolated from tests and generated files so the published artifact contains only the annotation contract.

## Data & Control Flow

Gradle compiles the `main` source set into the annotations artifact. Source annotations become compiler-visible metadata for processors and are not retained in runtime bytecode.

## Integration Points

Provides the `main` source tree consumed by the annotations module build. Downstream modules depend on the compiled artifact to reference the marker annotation during compilation.
