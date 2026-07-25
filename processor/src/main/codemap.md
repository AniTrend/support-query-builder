# processor/src/main/

## Responsibility

Production source root for the KSP processor implementation. It contains the Kotlin packages that register the processor, analyze annotated Room entities, model schema constants, and emit generated Kotlin source.

## Design Patterns

The implementation is organized by processing concern, entrypoint, code generation, model extraction, file factory, and symbol helper extensions. The production path is Kotlin-first and compiler API based.

## Data & Control Flow

KSP hands compiler symbols to `Processor`, which filters valid `EntitySchema` class declarations. The declarations are converted into `Candidate` models, translated into KotlinPoet items, then written to generated sources through the KSP `CodeGenerator`.

## Integration Points

Integrates with Room annotations for source metadata and with the `:annotations` module for the trigger annotation. Generated schema objects are intended for downstream modules that need stable table and column constants.
