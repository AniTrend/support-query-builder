# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/column/

## Responsibility

Models a generated constant for a direct Room column. `ColumnItem` maps a Kotlin property name to the resolved database column name.

## Design Patterns

Leaf item in the schema composite. It implements `Item` and writes one KotlinPoet `const val` string property using the source field name as the property identifier and the Room column name as the value.

## Data & Control Flow

`Candidate.getColumn` resolves `ColumnInfo.name`, falling back to the property name when Room inherits the field name. `ColumnItem.writeToBuilder` adds the constant to the schema object builder.

## Integration Points

Created by `Candidate` for direct entity properties and embedded type properties. Consumed by `TableItem` and `EmbedItem`, then ultimately emitted by `ClassFactory` through KotlinPoet.
