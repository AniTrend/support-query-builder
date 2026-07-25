# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/core/

## Responsibility

Defines the shared `Item` contract for model elements that can write generated schema properties into a KotlinPoet type builder.

## Design Patterns

Composite writer contract. Table, column, and embedded metadata all implement the same `writeToBuilder` operation so generation can combine heterogeneous schema parts uniformly.

## Data & Control Flow

`ClassFactory` creates a `TypeSpec.Builder` and delegates to a table `Item`. The table item then delegates to its child item implementations, each adding properties to the same builder.

## Integration Points

Implemented by `TableItem`, `ColumnItem`, and `EmbedItem`. Depends on KotlinPoet `TypeSpec.Builder` and is consumed by the factory package.
