---
description: Use when adding or changing public Kotlin APIs, KDoc, Dokka output, class docs, function docs, or property docs in support-query-builder modules.
applyTo: annotations/src/main/**/*.kt, core/src/main/**/*.kt, core/ext/src/main/**/*.kt, processor/src/main/**/*.kt
---

# KDoc And Dokka Guidance

- Treat KDoc as consumer documentation. The generated Dokka site is how downstream apps learn the library surface.
- Document every new or changed public or protected class, interface, object, enum, annotation, function, and property that a consumer may touch.
- Write documentation for someone outside this repo who does not already know the architecture. Explain what the API is for, when to use it, and which module or workflow it belongs to.
- For abstract base types such as `AbstractQueryBuilder`, document the extension contract: what subclasses must override, invariants they must preserve, and when callbacks are invoked.
- For builder types and DSL functions, document the expected call sequence, which methods are required vs optional, and how the final query is materialized.
- For extension functions such as `asSupportSQLiteQuery()`, document the receiver, side effects, threading or lifecycle assumptions, and any important nullability or mutation behavior.
- For annotation types such as `@EntitySchema`, document which annotation processor reads them, what code gets generated, and how the consumer should use the generated output.
- Preserve the existing house style when possible: a short summary first, then focused detail, with `@param`, `@property`, `@return`, `@throws`, `@see`, and `@since` where they add value.
- Do not invent version history. Only add `@since` when the version is already known or established in adjacent code.
- Avoid placeholder KDoc that only restates the type name. Explain behavior, expectations, and integration points.
- If behavior changes, update the docs in the same patch so the published site stays trustworthy.
- There are no internal package suppressions in this project; document all public-facing code.
