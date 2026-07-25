# sample/src/

## Responsibility

Source-set container for the sample app. The included production source set is `main`, which contains the manifest, Kotlin application code, and Android resources.

## Design Patterns

Separates production app code from other source sets by standard Android Gradle layout. Only `main` participates in the runtime architecture documented here.

## Data & Control Flow

The Android build consumes `main` sources, merges manifest and resources, compiles Kotlin, and links generated KSP schema classes into the sample app.

## Integration Points

Connected to the sample module Gradle configuration and Android plugin source-set discovery. Tests, generated output, and build output are outside this codemap scope.
