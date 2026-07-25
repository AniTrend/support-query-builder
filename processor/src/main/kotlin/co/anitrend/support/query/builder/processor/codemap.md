# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/

## Responsibility

Main processor package. It registers the KSP provider and runs annotation processing for `EntitySchema` classes before delegating schema file generation to codegen and factory packages.

## Design Patterns

- AutoService provider registration through `Provider`.
- KSP `SymbolProcessor` implementation in `Processor`.
- Round-aware validation, valid symbols are processed and invalid symbols are returned for a later round.
- Delegation to `EntitySchemaCodeGenerator` keeps symbol discovery separate from output assembly.

## Data & Control Flow

`Provider.create` receives the KSP environment and constructs `Processor` with the code generator, logger, and options. `Processor.process` resolves the `EntitySchema` annotation name, gets annotated symbols, partitions them by `validate()`, filters valid class declarations, groups them by parent declaration, and invokes the schema code generator for each group.

## Integration Points

Uses `co.anitrend.support.query.builder.annotation.EntitySchema` as the processing trigger. Integrates with KSP `Resolver`, `SymbolProcessorEnvironment`, `KSPLogger`, and `CodeGenerator`, then calls the local codegen package.
