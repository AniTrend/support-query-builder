# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/codegen/

## Responsibility

Coordinates conversion of valid KSP class declarations into schema generation candidates and starts file generation for each processing batch.

## Design Patterns

- Callable code generator abstraction, `EntitySchemaCodeGenerator` implements `ICodeGenerator.invoke`.
- Adapter stage between compiler symbols and model objects.
- Delegation to `ClassFactory` keeps metadata extraction separate from file writing.
- Structured KSP logging records inspected declarations and candidate batches.

## Data & Control Flow

The processor passes a resolver and a list of class declarations into `EntitySchemaCodeGenerator`. Each declaration is logged and wrapped as a `Candidate`. Non-empty candidate lists are passed to `ClassFactory.generateUsing`, which creates and writes Kotlin source files.

## Integration Points

Consumes KSP declarations from the processor package, builds model `Candidate` objects, and invokes the factory package. Depends on KSP `CodeGenerator`, `KSPLogger`, and `Resolver`.
