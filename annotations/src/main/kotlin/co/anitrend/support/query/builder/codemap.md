# annotations/src/main/kotlin/co/anitrend/support/query/builder/

## Responsibility

Scopes query builder public APIs owned by the annotations module. Its active child package contains the schema generation marker annotation.

## Design Patterns

Separates the builder namespace from the final annotation package so annotation contracts can evolve without mixing with processor or core builder implementations. This level is a namespace boundary only.

## Data & Control Flow

Compilation continues into the `annotation` child package to resolve compile-time markers. No runtime behavior is introduced in this directory.

## Integration Points

Anchors the public import path shared with the processor and with entity classes that opt into generated schema support.
