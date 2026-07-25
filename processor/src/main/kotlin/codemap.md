# processor/src/main/kotlin/

## Responsibility

Kotlin production namespace for the processor module. It hosts all processor classes, KSP extension helpers, metadata models, and KotlinPoet code generation components.

## Design Patterns

Package hierarchy mirrors ownership from broad organization namespace to concrete processor components. Internal model and factory classes keep generated source assembly separate from KSP symbol discovery.

## Data & Control Flow

Compiler control starts in `co.anitrend.support.query.builder.processor.Provider` and passes through `Processor`, `EntitySchemaCodeGenerator`, `Candidate`, item writers, and `ClassFactory`. Data moves from KSP declarations to Room-aware metadata to KotlinPoet file specs.

## Integration Points

Uses KSP symbols and logging, Room annotations, KotlinPoet builders, and `EntitySchema` from the annotations module. No Android runtime code is owned here, this package is compile-time only.
