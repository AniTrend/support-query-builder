# processor/src/main/kotlin/co/anitrend/support/query/builder/processor/model/field/

## Responsibility

Contains `FieldItem`, a small generic wrapper pairing a Java annotation processing `Element` with an annotation instance.

## Design Patterns

Simple data holder pattern. Unlike the active KSP model classes, this wrapper uses `javax.lang.model.element.Element` and does not implement the KotlinPoet `Item` writer contract.

## Data & Control Flow

No active production flow references `FieldItem` in `processor/src/main`. Current schema generation uses KSP declarations through `Candidate` instead.

## Integration Points

Depends only on Java annotation processing model types and Kotlin annotations. It is separate from Room metadata extraction, KSP symbol traversal, and KotlinPoet emission.
