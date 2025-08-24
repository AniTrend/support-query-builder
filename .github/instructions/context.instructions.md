---
applyTo: **
description: This file describes the architecture and module structure of the support-query-builder project.
---

# Support Query Builder Architecture

The support-query-builder project is a Kotlin library that provides a fluent SQL query builder with annotation processing capabilities for Android Room database integration. The library is designed to generate type-safe SQL queries dynamically while maintaining compile-time verification through annotation processing.

## Module Structure

The project is organized into distinct Gradle modules, each with specific responsibilities:

### Core Modules
- **`:core`** - The main query builder library containing the core SQL query construction logic. This module provides the fluent builder API with support for complex queries including joins, unions, and chaining operations. It contains no Android-specific dependencies, making it a pure Kotlin library that can be used in any JVM environment.
- **`:core:ext`** - Extension functions for the core module, primarily providing integration with Android Room's `SupportSQLiteQuery`. The main function `asSupportSQLiteQuery()` converts query builder instances to Room-compatible query objects.

### Annotation Processing
- **`:annotations`** - Contains the annotation definitions used to mark Room entities for processing. This module is dependency-free and only defines the annotations that other modules consume.
- **`:processor`** - Kotlin annotation processor (KAPT) that inspects Room entity annotations (`@Entity`, `@ColumnInfo`, `@Embedded`) and generates corresponding schema object classes. These generated objects mirror the entity structure and provide type-safe column references for the query builder.

### Development and Testing
- **`:sample`** - Example application demonstrating library usage. This module is excluded from CI builds but serves as a practical reference for integration patterns and usage examples.

## Architecture Principles

### Type Safety
The library emphasizes compile-time type safety through:
- Generated schema objects that mirror Room entity definitions
- Fluent builder API that prevents malformed SQL construction
- Extension functions that seamlessly integrate with Room's type system

### Room Integration
The library is designed specifically for Room database integration:
- Annotation processor reads Room entity annotations directly
- Generated queries are compatible with Room's `@RawQuery` annotation
- Support for Room's `SupportSQLiteQuery` interface through extension functions

### Modularity
The multi-module approach ensures:
- Core logic is separate from Android-specific integrations
- Annotation processing is isolated for clean build dependencies
- Extensions can be optionally included based on project needs

## Build System

The project uses Gradle with Kotlin DSL and includes:
- Custom build plugins in `buildSrc` for consistent configuration
- Android library configuration for Room compatibility
- Annotation processor configuration for code generation
- JitPack integration for distribution

## Key Components

### Query Builder Core
The core module provides a fluent API for constructing SQL SELECT queries with support for:
- Table selection and aliasing
- Column projection with type safety
- JOIN operations (INNER, LEFT, RIGHT, FULL)
- WHERE clause construction with parameter binding
- GROUP BY and HAVING clauses
- ORDER BY with multiple columns
- UNION and UNION ALL operations
- Subquery support

### Schema Generation
The annotation processor generates Kotlin object classes that:
- Mirror Room entity class structure
- Provide compile-time column name verification
- Support nested objects for `@Embedded` annotations
- Generate table metadata for query construction

### Room Extensions
Extension functions bridge the query builder with Room:
- Convert builder instances to `SupportSQLiteQuery`
- Handle parameter binding for dynamic queries
- Maintain compatibility with Room's query execution model

## Documentation

Full documentation is available at: https://anitrend.github.io/support-query-builder/

The documentation includes:
- API reference for all public classes and methods
- Integration guides for Room database usage
- Examples of complex query construction
- Best practices for annotation processor usage

This modular architecture ensures the library remains focused on its core purpose while providing flexible integration options for different use cases and maintaining compatibility with the Android ecosystem.