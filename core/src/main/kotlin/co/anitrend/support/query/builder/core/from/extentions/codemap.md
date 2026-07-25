# core/src/main/kotlin/co/anitrend/support/query/builder/core/from/extentions/

## Responsibility

Provides fluent construction helpers for FROM sources, aliases, and join chains. The package name is spelled `extentions` in source.

## Design Patterns

Uses infix and inline extension functions over `String`, `From`, and `AbstractQueryBuilder`. Join helpers return `From.Join.Partial` to enforce explicit join criteria through `on` before a final join is built.

## Data & Control Flow

String table names become `From.Table` objects, builders become `From.SubQuery`, aliases mutate `From.Aliasable`, and join helpers compose existing `from` state with a new source.

## Integration Points

Imported by the DSL layer and available to direct consumers building complex FROM clauses without using the full DSL surface.
