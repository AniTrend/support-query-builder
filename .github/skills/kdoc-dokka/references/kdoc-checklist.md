# KDoc Checklist

Use these prompts when documenting public APIs in support-query-builder.

## Class Or Interface

- What problem does this type solve?
- Which module or workflow is it part of (query construction, Room integration, code generation)?
- Should consumers instantiate it, subclass it, or only observe it?
- What collaborators or neighboring types matter?
- What builder call sequence, parameter binding expectations, or lifecycle assumptions matter?

Template:

```kotlin
/**
 * Short summary of the type and the workflow it belongs to.
 *
 * Explain when consumers should use, implement, or extend it.
 * Mention important collaborators with KDoc links.
 *
 * @property ...
 * @since ...
 */
```

## Builder Function Or DSL Function

- What clause or SQL fragment does it produce?
- When in the build sequence must it be called?
- Can it be called multiple times and what is the effect?
- What does it return (self for chaining, a new builder, a materialized query)?
- What can fail and how does failure surface?

Template:

```kotlin
/**
 * Short summary of the SQL clause or behavior this function adds.
 *
 * Describe call ordering requirements and chaining behavior.
 *
 * @param ...
 * @return ...
 * @throws ...
 */
```

## Property

- Is this configuration, state, or a contract consumers must provide?
- When is it read or updated within the builder lifecycle?
- Is it safe to mutate directly, or should callers use a builder method?

Template:

```kotlin
/**
 * Explains what this property represents and when consumers should read or set it.
 */
```

## Extension Function Or Property

- Document the receiver explicitly (e.g., `QueryBuilder`, `SupportSQLiteQuery`).
- Explain hidden dependencies such as Room, SQLite version, or threading context.
- Call out side effects and mutations (e.g., binds arguments to the query object).

## Annotation Type

- Which annotation processor reads this annotation?
- What code gets generated when it is applied?
- What constraints must the annotated element satisfy?
- How does the consumer use the generated output?

## Repo-Specific Reminders

- There are no `.internal` package suppressions in this project; document all consumer-facing surfaces.
- Use `@since` only when the version is known from existing code or release context.
- If the behavior changed, update the KDoc in the same patch so the published Dokka site stays trustworthy.
- Link neighboring types — for example, link `AbstractQueryBuilder` from `IQueryBuilder` and vice versa — so consumers can navigate the API surface easily.
