# core/ext/src/main/kotlin/co/anitrend/support/query/

## Responsibility

Query namespace segment for Android bridge code. It leads to the builder package that adapts core query builders for Android SQLite usage.

## Design Patterns

Uses namespace layering to separate query builder concerns from other support packages. Concrete behavior is still isolated in the descendant `builder.core.ext` package.

## Data & Control Flow

Build control descends through this namespace while compiling the bridge module. Runtime data enters the descendant extension function, where builder output becomes a `SupportSQLiteQuery`.

## Integration Points

Matches the core module package lineage, which helps consumers find the Android bridge beside the query builder API.
