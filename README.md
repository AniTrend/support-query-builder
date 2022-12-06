# [ :biohazard: W.I.P :biohazard: ] support-query-builder &nbsp; [![Run unit tests](https://github.com/AniTrend/support-query-builder/actions/workflows/android-test.yml/badge.svg)](https://github.com/AniTrend/support-query-builder/actions/workflows/android-test.yml) &nbsp; [![Codacy Badge](https://app.codacy.com/project/badge/Grade/2bcc9217df74403a9d4afd8664b20c34)](https://www.codacy.com/gh/AniTrend/support-query-builder/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=AniTrend/support-query-builder&amp;utm_campaign=Badge_Grade) &nbsp; [![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FAniTrend%2Fsupport-query-builder.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2FAniTrend%2Fsupport-query-builder?ref=badge_shield) &nbsp; [![](https://jitpack.io/v/AniTrend/support-query-builder.svg)](https://jitpack.io/#AniTrend/support-query-builder)

A simple yet comprehensive sql **select** query builder with featuring an annotation processor to generate schema objects from [Room](https://developer.android.com/reference/androidx/room/Room) annotations that plugs straight into [RawQuery](https://developer.android.com/reference/androidx/room/RawQuery)

## Why This Project Exists?

While [Room](https://developer.android.com/reference/androidx/room/Room) offers an excelent service loader based approach to generate an ORM layer for android application through static annotated queries, if you need to have some form of dynamic queries that might be user generated at runtime you would have to consider using [SupportSQLiteQueryBuilder](https://developer.android.com/reference/androidx/sqlite/db/SupportSQLiteQueryBuilder) to generate dynamic queries. However the [SupportSQLiteQueryBuilder](https://developer.android.com/reference/androidx/sqlite/db/SupportSQLiteQueryBuilder) API does a great job of constructing fairly simple queries, but lacks a fluent builder style API with joins, unions and large chains. In addition to this you have to write out table and column names as plain strings which is not only cumbersome but also error prone and adds additional overhead as you'd have to make sure that any changes you make to the entity related names are reflected throughout all your query builder references.

**support-query-builder** aims to solve these problems and comes in the form of 3 libraries with the following features:
- **annotations** - Annotation only which is used to inform the **processor** of entities to inspect
- **core** - The main query builder library for constructing queries
- **processor** - Kotlin annotation proccessor that generates kotlin object classes that mirror your Room entity annotations supporting inspection `@Entity`, `@ColumnInfo` and `@Embedded`


See a list of changes from [here](./CHANGELOG.md)

> Inspired by [QueryBuilder](https://github.com/reinaldoarrosi/QueryBuilder)

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FAniTrend%2Fsupport-query-builder.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FAniTrend%2Fsupport-query-builder?ref=badge_large)

____

## How Everything Works

### Getting Started

- __Add the JitPack repository to your build file__

```javascript
allprojects {
    repositories {
        ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

- __Add the dependency__

```javascript
dependencies {
    implementation 'com.github.anitrend:support-query-builder:module_name:{latest_version}'
}
```

### Examples

In all the instances you need to build a query you have to create an instance of the query builder:
```kotlin
import co.anitrend.support.query.builder.core.QueryBuilder

val builder = QueryBuilder()
```

Submitting your query builder into a [RawQuery](https://developer.android.com/reference/androidx/room/RawQuery) dao method for room to consume simply call:
```kotlin
val builder = QueryBuilder()
// ... statements here
builder.asSupportSQLiteQuery()
```

> **N.B.** Most of the builder extension functions are infix, please see the `co.anitrend.support.query.builder.dsl.*` package for more details and `co.anitrend.support.query.builder.core` test directory for a list of different examples

### Annotation processor

If you want to have your entity classes inspected and generate schema objects add the following to yout module gradle file

```javascript
dependencies {
    implementation 'com.github.anitrend:support-query-builder:annotaion:{latest_version}'
    kapt 'com.github.anitrend:support-query-builder:processor:{latest_version}'
}
```

After you can annotate your entity classes with `@EnititySchema` as shown below, which should only be on your top level entity e.g.:

```kotlin
@EntitySchema
@Entity(tableName = "pet")
internal data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "owner_id") val owner: Long,
    @Embedded(prefix = "breed_") val breed: Breed,
) {
    data class Breed(
        @ColumnInfo(name = "group") val group: String,
        @ColumnInfo(name = "origin") val origin: String
    )

    // This should not be generated since it is not referenced in the entity
    data class SomeConstruct(
        @ColumnInfo(name = "id") val id: Long,
        @Embedded(prefix = "prob_") val property: Property
    ) {
        data class Property(
            @ColumnInfo(name = "option_a") val optionA: String
        )
    }
}
```

When your build completes your should be able to access a generated object called `PetEntitySchema` which would have the following format.

```kotlin
public object PetEntitySchema {
  public const val tableName: String = "pet"

  public const val id: String = "id"

  public const val name: String = "name"

  public const val owner: String = "owner_id"

  public const val breedGroup: String = "breed_group"

  public const val breedOrigin: String = "breed_origin"
}
```

You may use the newly created schema object when building out your queries

> **N.B.** If you do not set the `tableName` protery on @Entity then the class name is used instead, the same applies to `name` property on `@ColumnInfo` and `prefix` on `@Embedded`
> **Check out the sample project and tests located [in the core module](./core/src/test/kotlin/co/anitrend/support/query/builder/core) for more samples**

### Basic statement

> ```sql
>SELECT * FROM table_name
>```
```kotlin
val builder = QueryBuilder()
builder select "*" from "table_name"
```

Which can also be written as

```kotlin
val builder = QueryBuilder()
builder from "table_name"
```


### Basic statement with alias

> ```sql
>SELECT column_name AS t FROM table_name
>```
```kotlin
val builder = QueryBuilder()
val column = "column_name".asColumn()
builder select (column `as` "t") from "table_name"
```

Which can also be applied on the table name

> ```sql
>SELECT * FROM table_name AS n
>```
```kotlin
val builder = QueryBuilder()
builder from ("table_name".asTable() `as` "n")
```


### Basic statement with where clause

> ```sql
>SELECT * FROM table_name WHERE column_name = 'something'
>```
```kotlin
val builder = QueryBuilder()
val column = "column_name".asColumn()
builder from table where {
    column equal "something"
}
```

Which can also be written as
```kotlin
val builder = QueryBuilder()
val column = "column_name".asColumn()
builder.from(table).where(column.equal("something"))
```


### Statement with inner join clause

> ```sql
>SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id
>```
```kotlin
val builder = QueryBuilder()
builder from table.innerJoin("other_table_name") {
    on("other_column_id", "column_id")
}
```

Which can also be written as
```kotlin
val builder = QueryBuilder()
builder.from(table.innerJoin("other_table_name").on("other_column_id", "column_id"))
```


### Statement with inner join and where clause and order by

> ```sql
>SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id WHERE column_name = 'something'
>```
```kotlin
val builder = QueryBuilder()
val column = "column_name".asColumn()
// You can ommit the `.asTable` if you want
builder from table.innerJoin("other_table_name".asTable()) {
    on("other_column_id", "column_id")
} where {
    column equal "something"
} orderByDesc column
```

### Statement with inner join and where clause and filter

> ```sql
>SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id WHERE (column_name = 'something' AND column_name LIKE '%pe')
>```
```kotlin
val builder = QueryBuilder()
val column = "column_name".asColumn()
builder from {
    table.innerJoin("other_table_name").on(
        "other_column_id", "column_id"
    )
} where {
    column.equal("something") and column.endsWith("pe")
}
```


### Statement with multiple join and where clause and filter

> ```sql
>SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id LEFT JOIN some_table_name ON some_other_column_id = column_id WHERE (column_name = 'something' AND column_name LIKE '%pe')
>```
```kotlin
val builder = QueryBuilder()
val column = "column_name".asColumn()
builder from {
    table.innerJoin("other_table_name").on(
        "other_column_id", "column_id"
    )
    .leftJoin("some_table_name").
        on("some_other_column_id", "column_id")

} where {
    (column equal "something") and (column endsWith "pe")
}
```

Which can also be written as:

```kotlin
builder from table
builder from {
    innerJoin("other_table_name") {
        on("other_column_id", "column_id")
    }
}
builder from {
    leftJoin("some_table_name").on(
        "some_other_column_id", "column_id"
    )
}
builder where {
    (column equal "something") and (column endsWith "pe")
}
```


## License

```
Copyright 2021 AniTrend

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
