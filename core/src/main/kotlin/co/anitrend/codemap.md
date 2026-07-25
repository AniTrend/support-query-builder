# core/src/main/kotlin/co/anitrend/

## Responsibility

Owns the AniTrend namespace for the core query builder module.

## Design Patterns

Acts as a package boundary. Implementation remains under the support query builder packages.

## Data & Control Flow

No executable control flow is defined here. Runtime query construction is delegated to descendants.

## Integration Points

Preserves the shared namespace used by core, Android extension, processor output references, and sample code.
