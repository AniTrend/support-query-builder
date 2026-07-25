# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/extensions/

## Responsibility

Provides small KSP annotation lookup helpers used while extracting Room metadata from declarations and annotation arguments.

## Design Patterns

Kotlin extension functions keep KSP API traversal concise at call sites. Helpers search annotation collections by short class name and argument names derived from Kotlin properties.

## Data & Control Flow

`Candidate` calls these extensions to find annotations on class or property declarations, then reads annotation arguments such as Room column names, entity table names, and embedded prefixes.

## Integration Points

Works with KSP `KSClassDeclaration`, `KSDeclaration`, `KSAnnotation`, and `KSValueArgument`. Supports Room metadata extraction in the model package without taking a dependency on KotlinPoet or file emission.
