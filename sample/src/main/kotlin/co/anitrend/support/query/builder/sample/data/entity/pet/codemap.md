# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/pet/

## Responsibility

Pet aggregate for the sample Room schema. `PetEntity` maps the `pet` table, its owner relationship, and embedded breed columns, with child packages for DAO and query helpers.

## Design Patterns

Uses Room entity annotations plus `@EntitySchema` generation. The entity declares an owner index and foreign key to `PersonEntity`, while the embedded `Breed` object flattens columns with the `breed_` prefix.

## Data & Control Flow

Room maps pet rows into `PetEntity` instances. `PetQueryBuilder` targets generated schema columns and sends raw queries through `PetDao` for filtered result retrieval.

## Integration Points

Registered by `Store`, linked to `PersonEntity` through `owner_id`, processed by the schema processor, and queried through Room DAO methods.
