# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/table/

## Responsibility

Models a Room table for generated schema output. `TableItem` writes the table name constant and delegates column constant generation to its child items.

## Design Patterns

Composite root for schema items. It implements `Item`, stores table name plus column and embedded item lists, and uses KotlinPoet `PropertySpec` to emit a `const val tableName`.

## Data & Control Flow

`Candidate.getTable` creates `TableItem` from Room `Entity` metadata and declared properties. During generation, `writeToBuilder` adds `tableName`, then iterates over direct columns and embedded items so each writes its own constants.

## Integration Points

Consumes `Item` children from the column and embed packages. Provides the top-level item consumed by `ClassFactory` when building generated schema objects.
