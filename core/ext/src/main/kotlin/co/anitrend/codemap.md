# core/ext/src/main/kotlin/co/anitrend/

## Responsibility

Organization namespace segment for production bridge code. It contains the support query builder package branch used by the Android extension API.

## Design Patterns

Uses package nesting to keep public API names consistent across modules. The folder has structural ownership only, with concrete adapter behavior located further down the tree.

## Data & Control Flow

Gradle and Kotlin compilation traverse this namespace to reach the extension source file. Runtime query conversion continues to be controlled by the concrete extension function in the descendant package.

## Integration Points

Connects the Android bridge to the common AniTrend package structure shared by `:core` and related modules.
