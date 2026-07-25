# processor/

## Responsibility

Compile-time KSP processor module that turns Room entity metadata annotated with `@EntitySchema` into Kotlin schema objects. The module owns processor registration, symbol scanning, Room annotation extraction, KotlinPoet model assembly, and generated file emission.

## Design Patterns

- KSP provider pattern, `Provider` exposes `Processor` through AutoService discovery.
- Small pipeline stages, processor entrypoint, code generator, candidate model, item writers, and file factory.
- KotlinPoet builder pattern, schema output is assembled as object and property specs before writing.
- Incremental processing support, generated files attach isolating KSP dependencies when source files are available.

## Data & Control Flow

KSP invokes `Provider.create`, then `Processor.process` queries symbols annotated with `EntitySchema`. Valid class declarations are grouped by parent declaration and passed to `EntitySchemaCodeGenerator`. Each declaration becomes a `Candidate`, Room `Entity`, `ColumnInfo`, and `Embedded` metadata becomes `Item` implementations, and `ClassFactory` writes a `<EntityName>Schema` object.

## Integration Points

Depends on `:annotations` for `EntitySchema`, KSP APIs for compiler integration, AutoService for service registration, KotlinPoet for output construction, and Room common annotations for metadata extraction. Consumers wire this module through KSP, commonly via `ksp(project(":processor"))`.
