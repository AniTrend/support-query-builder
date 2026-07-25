# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/codegen/contract/

## Responsibility

Defines the callable contract for schema code generation stages that consume KSP resolver context and class declarations.

## Design Patterns

Single-method interface with `operator fun invoke` so implementations can be called like functions. This keeps the processor delegation concise while allowing alternative generator implementations.

## Data & Control Flow

The processor calls an `ICodeGenerator` with a `Resolver` and validated `KSClassDeclaration` list. Implementations decide how to analyze declarations and emit output.

## Integration Points

Currently implemented by `EntitySchemaCodeGenerator`. Depends only on KSP `Resolver` and `KSClassDeclaration`, keeping the contract independent from KotlinPoet and file factory details.
