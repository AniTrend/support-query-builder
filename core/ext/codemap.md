# core/ext/

## Responsibility

Android bridge module for adapting core query builder output into AndroidX SQLite query objects. The module owns Android namespace configuration, module dependencies, and the production source set that exposes the Room compatible API.

## Design Patterns

Uses an adapter pattern at the module boundary, keeping AndroidX SQLite types outside `:core`. The Gradle script declares the Android namespace, depends on `:core` and `androidx.sqlite.ktx`, and makes lint model tasks wait for `:core:classesJar`.

## Data & Control Flow

Consumers create an `AbstractQueryBuilder` from the core module, call the extension API in this module, and receive a `SupportSQLiteQuery`. The adapter delegates SQL text and bind parameter extraction back to the builder before wrapping both in `SimpleSQLiteQuery`.

## Integration Points

Integrates with `:core` for query construction, AndroidX SQLite for `SupportSQLiteQuery`, and downstream Room DAO methods that accept raw queries. The module is configured as an Android library through the shared project plugin.
