# annotations/src/main/

## Responsibility

Hosts production sources for the annotations artifact. The current content is the Kotlin package tree that defines the entity schema marker annotation.

## Design Patterns

Uses a minimal production source set with no runtime resources in this subtree. The layout keeps the annotation API independent of processor implementation details.

## Data & Control Flow

Compilation walks the Kotlin source tree, resolves `EntitySchema`, and exposes it to source analysis tools during the same build. No runtime control flow is implemented here.

## Integration Points

Integrates with the annotations module Gradle configuration as its primary source set. The generated artifact is referenced by KSP processing and by source code that declares schema generation intent.
