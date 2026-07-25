# core/ext/src/main/

## Responsibility

Production source root for the Android Room bridge. It contains the Kotlin namespace tree that exposes the `AbstractQueryBuilder` to `SupportSQLiteQuery` conversion API.

## Design Patterns

Uses a minimal main source set with Kotlin code only. The bridge avoids resources or manifest level behavior and keeps runtime adaptation in a single package path.

## Data & Control Flow

During builds, Kotlin files below this root are compiled into the Android library. At runtime, callers enter the package API and the bridge produces AndroidX SQLite query objects from core builder state.

## Integration Points

Integrated by the Android Gradle Plugin as the module production source set. The compiled output links `:core` query contracts with AndroidX SQLite classes available to Room consumers.
