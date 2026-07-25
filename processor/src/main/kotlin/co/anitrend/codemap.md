# processor/src/main/kotlin/co/anitrend/

## Responsibility

Organization namespace for AniTrend processor source. It scopes the support query builder KSP implementation under the project package identity.

## Design Patterns

Namespace aggregation layer. Concrete classes remain in lower packages so compiler integration and model code are separated by feature area.

## Data & Control Flow

No direct processing flow starts here. KSP-loaded code is located in descendant packages, especially `support.query.builder.processor`.

## Integration Points

Provides stable package ancestry for the processor module and generated schema code ownership.
