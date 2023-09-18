/*
 * Copyright 2023 AniTrend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.anitrend.support.query.builder.core.from.extentions

import co.anitrend.support.query.builder.core.contract.AbstractQueryBuilder
import co.anitrend.support.query.builder.core.from.From

/**
 * Create a [From.Table] from a [String]
 */
fun String.asTable() =
    From.Table(this)

/**
 * Create a sub query from the builder
 *
 * @return [From.SubQuery]
 */
fun AbstractQueryBuilder.asSubQuery() =
    From.SubQuery(this)

/**
 * Create an alias from any type of [From.Aliasable]
 *
 * __Usage:__
 * ```
 * "table_name".asTable() `as` "n"
 * ```
 *
 * @see asTable
 *
 * @return [From.Aliasable]
 */
infix fun From.Aliasable.`as`(name: String) = also { aliasAs(name) }

/**
 * Create an inner join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.innerJoin("other_table_name".asTable())
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun From.innerJoin(other: From) =
    From.Join.Partial(this, other, From.Join.Type.INNER)

/**
 * Create an inner join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.innerJoin("other_table_name")
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun From.innerJoin(other: String) = innerJoin(other.asTable())

/**
 * Creates an inner join on the receiver with [other]
 *
 * __Usage:__
 * ```
 * from {
 *     leftJoin("some_table_name").on(
 *         "some_other_column_id", "column_id"
 *     )
 * }
 * ```
 *
 * @see innerJoin
 */
infix fun AbstractQueryBuilder.innerJoin(other: From) =
    requireNotNull(from) {
        "`from` has not been set on your query yet"
    }.innerJoin(other)

/**
 * Creates an inner join on the receiver with [other]
 *
 * __Usage:__
 * ```
 * from {
 *     innerJoin("some_table_name").on(
 *         "some_other_column_id", "column_id"
 *     )
 * }
 * ```
 *
 * @see innerJoin
 */
infix fun AbstractQueryBuilder.innerJoin(other: String) = innerJoin(other.asTable())

/**
 * Create an inner join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from "table_name".innerJoin("other_table_name")
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun String.innerJoin(other: String) =
    asTable().innerJoin(other.asTable())

/**
 * Create an inner join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.innerJoin("other_table_name".asTable()) {
 *     on("other_column_id", "column_id")
 * }
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
inline fun From.innerJoin(
    other: From.Table,
    block: From.Join.Partial.() -> From,
) = let {
    val result = innerJoin(other)
    block(result)
}

/**
 * Create an inner join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.innerJoin("other_table_name") {
 *     on("other_column_id", "column_id")
 * }
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
inline fun From.innerJoin(
    other: String,
    block: From.Join.Partial.() -> From,
) = innerJoin(other.asTable(), block)

/*-----------------------------------------------------------------*/

/**
 * Create an left join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.leftJoin("other_table_name".asTable())
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun From.leftJoin(other: From) =
    From.Join.Partial(this, other, From.Join.Type.LEFT)

/**
 * Create an left join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.leftJoin("other_table_name")
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun From.leftJoin(other: String) = leftJoin(other.asTable())

/**
 * Creates an inner join on the receiver with [other]
 *
 * __Usage:__
 * ```
 * from {
 *     leftJoin("some_table_name").on(
 *         "some_other_column_id", "column_id"
 *     )
 * }
 * ```
 *
 * @see leftJoin
 */
infix fun AbstractQueryBuilder.leftJoin(other: From) =
    requireNotNull(from) {
        "`from` has not been set on your query yet"
    }.leftJoin(other)

/**
 * Creates an inner join on the receiver with [other]
 *
 * __Usage:__
 * ```
 * from {
 *     leftJoin("some_table_name").on(
 *         "some_other_column_id", "column_id"
 *     )
 * }
 * ```
 *
 * @see leftJoin
 */
infix fun AbstractQueryBuilder.leftJoin(other: String) = leftJoin(other.asTable())

/**
 * Create an left join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from "table_name".leftJoin("other_table_name")
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun String.leftJoin(other: String) =
    asTable().leftJoin(other.asTable())

/**
 * Create an left join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.leftJoin("other_table_name".asTable()) {
 *     on("other_column_id", "column_id")
 * }
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
inline fun From.leftJoin(
    other: From.Table,
    block: From.Join.Partial.() -> From,
) = let {
    val result = leftJoin(other)
    block(result)
}

/**
 * Create an left join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.leftJoin("other_table_name") {
 *     on("other_column_id", "column_id")
 * }
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
inline fun From.leftJoin(
    other: String,
    block: From.Join.Partial.() -> From,
) = leftJoin(other.asTable(), block)

/*-----------------------------------------------------------------*/

/**
 * Create an cross join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.crossJoin("other_table_name".asTable())
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun From.crossJoin(other: From) =
    From.Join.Partial(this, other, From.Join.Type.CROSS)

/**
 * Create an cross join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.crossJoin("other_table_name")
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun From.crossJoin(other: String) = crossJoin(other.asTable())

/**
 * Creates an cross join on the receiver with [other]
 *
 * __Usage:__
 * ```
 * from {
 *     crossJoin("some_table_name").on(
 *         "some_other_column_id", "column_id"
 *     )
 * }
 * ```
 *
 * @see crossJoin
 */
infix fun AbstractQueryBuilder.crossJoin(other: From) =
    requireNotNull(from) {
        "`from` has not been set on your query yet"
    }.crossJoin(other)

/**
 * Creates an cross join on the receiver with [other]
 *
 * __Usage:__
 * ```
 * from {
 *     crossJoin("some_table_name").on(
 *         "some_other_column_id", "column_id"
 *     )
 * }
 * ```
 *
 * @see crossJoin
 */
infix fun AbstractQueryBuilder.crossJoin(other: String) = crossJoin(other.asTable())

/**
 * Create an cross join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from "table_name".crossJoin("other_table_name")
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun String.crossJoin(other: String) =
    asTable().crossJoin(other.asTable())

/**
 * Create an cross join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.crossJoin("other_table_name".asTable()) {
 *     on("other_column_id", "column_id")
 * }
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
inline fun From.crossJoin(
    other: From.Table,
    block: From.Join.Partial.() -> From,
) = let {
    val result = crossJoin(other)
    block(result)
}

/**
 * Create an cross join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.crossJoin("other_table_name") {
 *     on("other_column_id", "column_id")
 * }
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
inline fun From.crossJoin(
    other: String,
    block: From.Join.Partial.() -> From,
) = crossJoin(other.asTable(), block)

/*-----------------------------------------------------------------*/

/**
 * Create an join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.join("other_table_name".asTable())
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun From.join(other: From) =
    From.Join.Partial(this, other, From.Join.Type.JOIN)

/**
 * Create an join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.join("other_table_name")
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun From.join(other: String) = join(other.asTable())

/**
 * Creates an join on the receiver with [other]
 *
 * __Usage:__
 * ```
 * from {
 *     join("some_table_name").on(
 *         "some_other_column_id", "column_id"
 *     )
 * }
 * ```
 *
 * @see join
 */
infix fun AbstractQueryBuilder.join(other: From) =
    requireNotNull(from) {
        "`from` has not been set on your query yet"
    }.join(other)

/**
 * Creates an join on the receiver with [other]
 *
 * __Usage:__
 * ```
 * from {
 *     join("some_table_name").on(
 *         "some_other_column_id", "column_id"
 *     )
 * }
 * ```
 *
 * @see join
 */
infix fun AbstractQueryBuilder.join(other: String) = join(other.asTable())

/**
 * Create an join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from "table_name".join("other_table_name")
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
infix fun String.join(other: String) =
    asTable().join(other.asTable())

/**
 * Create an join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.join("other_table_name".asTable()) {
 *     on("other_column_id", "column_id")
 * }
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
inline fun From.join(
    other: From.Table,
    block: From.Join.Partial.() -> From,
) = let {
    val result = join(other)
    block(result)
}

/**
 * Create an join on the receiver as with [other]
 *
 * __Usage:__
 * ```
 * builder from table.join("other_table_name") {
 *     on("other_column_id", "column_id")
 * }
 * ```
 *
 * @see [From.Join.Partial.on]
 * @see [From.Join.Type]
 *
 * @return [From.Join.Partial]
 */
inline fun From.join(
    other: String,
    block: From.Join.Partial.() -> From,
) = join(other.asTable(), block)
