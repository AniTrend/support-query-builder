# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/

## Responsibility

Owns the metadata model that converts KSP class declarations and Room annotations into generated schema items. `Candidate` is the main extraction unit for table, column, and embedded field metadata.

## Design Patterns

- Candidate model wraps a `KSClassDeclaration` and exposes generated package, class, and file naming.
- Composite item model, `TableItem` owns child `ColumnItem` and `EmbedItem` writers through the shared `Item` contract.
- Room annotation adapter, KSP symbols are translated into KotlinPoet-ready constants.
- Logging is embedded in extraction paths to report missing annotations or fallback names.

## Data & Control Flow

`Candidate.getTable` reads the Room `Entity` table name or falls back to the class name. It inspects declared properties for `ColumnInfo`, maps them to `ColumnItem`, then inspects embedded properties for `Embedded` prefixes and nested declared columns. The resulting `TableItem` writes a `tableName` constant followed by column constants into a KotlinPoet type builder.

## Integration Points

Consumes Room `Entity`, `ColumnInfo`, and `Embedded` annotations through KSP symbols. Uses extension helpers for annotation lookup and KotlinPoet `TypeSpec.Builder` through model item writers. Supplies generated metadata to `ClassFactory`.
