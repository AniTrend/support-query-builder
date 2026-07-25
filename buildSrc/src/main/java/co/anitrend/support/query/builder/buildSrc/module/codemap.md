# buildSrc/src/main/java/co/anitrend/support/query/builder/buildSrc/module/

## Responsibility

Defines canonical Gradle module identifiers used by the convention plugin.

## Design Patterns

Uses an internal object containing enum groups for app, processor, and common modules. Each enum implements a shared `Module` contract with `id` and a `path()` formatter.

## Data & Control Flow

Extension predicates compare `Project.name` against these ids to classify sample, annotations, core, core ext, and processor modules. The `path()` helper produces Gradle project path strings when a typed module path is needed.

## Integration Points

Consumed by project extension helpers and plugin configuration decisions. Keeps module names in one place for Android, Kotlin JVM, publishing, and dependency branching.
