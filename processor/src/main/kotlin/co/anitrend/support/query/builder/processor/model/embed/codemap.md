# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/embed/

## Responsibility

Models generated constants for Room embedded properties. `EmbedItem` combines an embedded property name, optional Room prefix, and the embedded type columns into prefixed schema constants.

## Design Patterns

Composite leaf writer for nested columns. It derives generated constant names from the embedded field plus title-cased column names, while constant values combine the Room embedded prefix and column database name.

## Data & Control Flow

`Candidate.getEmbeddings` finds `Embedded` annotations, reads the `prefix` argument, resolves the embedded type declaration, and extracts its `ColumnInfo` properties as `ColumnItem` values. `EmbedItem.writeToBuilder` emits one constant per nested column.

## Integration Points

Consumes column items from the column package and implements the shared `Item` contract. Generated output is included by `TableItem` and written through the normal KotlinPoet factory path.
