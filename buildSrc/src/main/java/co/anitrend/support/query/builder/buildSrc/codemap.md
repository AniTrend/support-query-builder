# buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/

## Responsibility

Implementation root for the repository's Gradle convention plugin. It owns module identity, Gradle extension helpers, plugin orchestration, component configuration, and dependency selection.

## Design Patterns

Uses a plugin facade plus cohesive helper packages. `CorePlugin` coordinates, `module` defines canonical module ids, `extension` wraps Gradle APIs, `plugins.components` configures build features, and `plugins.strategy` encapsulates dependency defaults.

## Data & Control Flow

Gradle resolves the plugin marker to `CorePlugin`. The plugin applies platform plugins, logs available extensions and components, configures Android when needed, applies dependencies, and registers sources and Maven publishing for non-sample modules.

## Integration Points

Integrates with module names from the repository graph, Android application and library extensions, Kotlin JVM extension, Maven Publish, Dokka, Spotless, and shared version catalog accessors.
