# annotations/src/main/kotlin/co/anitrend/

## Responsibility

Owns the AniTrend package namespace for annotation sources. It scopes the query builder annotation API under the organization package.

## Design Patterns

Functions as a package boundary rather than an implementation layer. Keeping the namespace explicit supports stable imports for downstream source and processor code.

## Data & Control Flow

No runtime data or control flow is declared here. Build-time traversal continues into the support query builder packages where the source-retained marker is defined.

## Integration Points

Provides the organizational prefix used by the annotations artifact and by consumers importing `co.anitrend.support.query.builder.annotation.EntitySchema`.
