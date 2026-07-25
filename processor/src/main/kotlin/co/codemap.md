# processor/src/main/kotlin/co/

## Responsibility

Top-level Kotlin package namespace for production processor code under `co.anitrend`. This folder exists to preserve the published package path.

## Design Patterns

Namespace-only package layer. It delegates all concrete processing responsibility to nested `anitrend` packages without adding code of its own.

## Data & Control Flow

No symbols are defined directly at this level. Control and metadata flow continue into `co.anitrend.support.query.builder.processor`.

## Integration Points

Maintains package compatibility for the processor artifact and generated compiler services.
