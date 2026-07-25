# buildSrc/src/main/java/co/anitrend/support/

## Responsibility

Support library namespace segment for buildSrc tooling.

## Design Patterns

Functions as a package grouping layer. Implementation remains in deeper query builder specific packages.

## Data & Control Flow

No executable logic is declared at this level. Gradle references traverse it as part of the fully qualified plugin class name.

## Integration Points

Keeps buildSrc package ownership aligned with the support query builder production modules.
