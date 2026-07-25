# buildSrc/src/main/java/co/anitrend/

## Responsibility

Organization namespace for Anitrend build logic inside buildSrc.

## Design Patterns

Provides hierarchical ownership for support query builder build tooling. It contains namespace folders only, with implementation below `support/query/builder/buildSrc`.

## Data & Control Flow

Control flow passes through this namespace when Gradle loads `co.anitrend.support.query.builder.buildSrc.plugins.CorePlugin`.

## Integration Points

Aligns buildSrc plugin package naming with the repository group and plugin id prefix.
