# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/entity/person/

## Responsibility

Person aggregate for the sample Room schema. `PersonEntity` maps the `person` table and nested city value object, while child packages provide DAO and query helpers.

## Design Patterns

Uses Room entity annotations with `@EntitySchema` for code generation. The embedded `City` object uses the `city_` prefix to flatten `name`, `region`, and `country` into table columns.

## Data & Control Flow

Room materializes query rows into `PersonEntity` instances. Filter requests from `PersonQueryBuilder` target generated schema column names for direct raw-query execution through `PersonDao`.

## Integration Points

Registered by `Store`, referenced by `PetEntity` as a foreign key parent, processed by `:processor`, and queried through Room DAO methods.
