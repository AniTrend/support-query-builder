# buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/plugins/

## Responsibility

Owns the Gradle convention plugin entrypoint and plugin-level orchestration.

## Design Patterns

Implements a facade plugin through `CorePlugin`, with configuration details delegated to component functions and dependency strategy classes. Diagnostic helpers log available Gradle extensions and components.

## Data & Control Flow

`CorePlugin.apply` first applies required Gradle plugins, logs project extension and component schemas, configures Android for non-Kotlin-library modules, applies dependencies, and configures sources for non-sample modules.

## Integration Points

Referenced by the Gradle plugin marker resource. Delegates to `plugins.components`, `plugins.strategy`, and `extension` helpers, and relies on module classification from `module`.
