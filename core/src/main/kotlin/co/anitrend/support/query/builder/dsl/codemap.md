# core/src/main/kotlin/co/anitrend/support/query/builder/dsl/

## Responsibility

Exposes the high-level Kotlin DSL for mutating an `AbstractQueryBuilder` into a complete SELECT query.

## Design Patterns

Uses infix and inline extension functions over `AbstractQueryBuilder` to provide readable select, from, join, where, group, order, pagination, distinct, and union operations. DSL functions return the receiver for chaining.

## Data & Control Flow

DSL calls append projections, set a source, compose criteria with AND or OR, append grouping and ordering, set pagination flags, and attach union queries. Rendering remains delegated to `QueryBuilder` and clause objects.

## Integration Points

Imports clause factories from criteria, from, order, and projection packages. Intended for JVM consumers and Android callers before converting the built output to Room raw-query types elsewhere.
