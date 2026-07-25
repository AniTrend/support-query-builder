# processor/src/

## Responsibility

Source-set container for the processor module. Production code lives under `main` and implements the KSP annotation processing path for schema generation.

## Design Patterns

The source tree follows Gradle source-set separation. Production concerns stay in `src/main`, while generated output and tests are outside this documented path.

## Data & Control Flow

Build tooling compiles the `main` source set into a KSP processor artifact. Runtime control is compiler-driven, KSP loads the registered provider and calls into the processor during consuming builds.

## Integration Points

Configured by `processor/build.gradle.kts`, which applies the shared query builder plugin and KSP plugin, then adds annotation, KSP, KotlinPoet, Room common, and AutoService dependencies for production processing.
