# annotations/src/main/kotlin/co/anitrend/support/query/builder/annotation/

## Responsibility

Defines `EntitySchema`, the source-retained class annotation that marks an entity for schema object generation.

## Design Patterns

Uses a marker annotation pattern with `AnnotationTarget.CLASS` to restrict usage to class declarations. `AnnotationRetention.SOURCE` keeps the marker available to source processors while avoiding runtime retention.

## Data & Control Flow

A consumer annotates a class with `@EntitySchema`. During compilation, KSP can identify the annotated class, read companion Room metadata, and trigger schema generation in the processor module. The annotation itself has no properties and carries intent only through its presence.

## Integration Points

Imported by entity source that wants generated schema constants. Referenced by the processor module as the discovery annotation for schema candidates. Published as part of the annotations artifact built from this module.
