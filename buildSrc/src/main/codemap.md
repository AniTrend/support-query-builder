# buildSrc/src/main/

## Responsibility

Production source set for the repository convention plugin. It contains the plugin implementation package and Gradle plugin marker resources.

## Design Patterns

Uses a conventional Gradle plugin layout, source code under `java` and plugin registration under `resources/META-INF/gradle-plugins`. Implementation code is decomposed into module identity, extension helpers, plugin orchestration, component configuration, and dependency strategy.

## Data & Control Flow

Gradle packages the marker properties and compiled plugin classes together. When a consuming module applies the plugin id, the marker resolves to `CorePlugin`, which coordinates all project configuration.

## Integration Points

Connects buildSrc compilation to the repository's Gradle runtime. The plugin uses Android, Kotlin, Dokka, Spotless, Maven Publish, and version catalog APIs supplied by buildSrc dependencies.
