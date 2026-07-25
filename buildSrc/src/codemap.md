# buildSrc/src/

## Responsibility

Source root for the buildSrc convention plugin. It separates plugin implementation and resources from Gradle build configuration.

## Design Patterns

Follows standard Gradle source set layout. Production logic lives under `main`, with no documented test or generated paths in scope for this codemap.

## Data & Control Flow

Gradle compiles `src/main/java` Kotlin sources and packages `src/main/resources` into the buildSrc output. The plugin marker resource points Gradle to the implementation class.

## Integration Points

Feeds the buildSrc jar consumed automatically by the repository build. Exposes plugin code and marker metadata to module build scripts.
