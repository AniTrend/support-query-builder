# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/factory/

## Responsibility

Owns final Kotlin source file construction and emission for generated schema objects. `ClassFactory` converts candidate model output into KotlinPoet file specs and writes them through KSP.

## Design Patterns

- Factory pattern around generated object creation and file commit.
- KotlinPoet builder pattern, `TypeSpec.Builder` receives table and column properties through model items.
- Duplicate emission guard with an in-memory key set per factory instance.
- Incremental KSP dependency selection, isolating when the origin source file is known, aggregating fallback otherwise.

## Data & Control Flow

`generateUsing` receives `Candidate` instances, creates an object builder named after each schema file, asks the table item to write properties, builds a `FileSpec`, then commits it to `CodeGenerator.createNewFile`. Existing files from repeated rounds are logged and skipped.

## Integration Points

Consumes `Candidate` and item models from `model`. Writes through KSP `CodeGenerator` and `Dependencies`, logs through `KSPLogger`, and uses KotlinPoet `FileSpec` and `TypeSpec`.
