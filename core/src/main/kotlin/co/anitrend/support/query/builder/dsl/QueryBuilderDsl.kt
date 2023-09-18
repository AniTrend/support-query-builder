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

package co.anitrend.support.query.builder.dsl

import co.anitrend.support.query.builder.core.contract.AbstractQueryBuilder
import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.criteria.extensions.and
import co.anitrend.support.query.builder.core.criteria.extensions.or
import co.anitrend.support.query.builder.core.from.From
import co.anitrend.support.query.builder.core.from.extentions.asTable
import co.anitrend.support.query.builder.core.from.extentions.crossJoin
import co.anitrend.support.query.builder.core.from.extentions.innerJoin
import co.anitrend.support.query.builder.core.from.extentions.join
import co.anitrend.support.query.builder.core.from.extentions.leftJoin
import co.anitrend.support.query.builder.core.order.extensions.orderAsc
import co.anitrend.support.query.builder.core.order.extensions.orderDesc
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.asColumn

fun AbstractQueryBuilder.select(
    projections: List<Projection>,
) = also { this.projections.addAll(projections) }

fun AbstractQueryBuilder.select(
    vararg columns: String,
) = select(columns.asColumn())

infix fun AbstractQueryBuilder.select(
    column: String,
) = also { this.projections.add(column.asColumn()) }

infix fun AbstractQueryBuilder.select(
    column: Projection,
) = also { this.projections.add(column) }

infix fun AbstractQueryBuilder.from(
    from: From,
) = also { this.from = from }

infix fun AbstractQueryBuilder.from(
    subQuery: IQueryBuilder,
) = also { this.from = From.SubQuery(subQuery) }

infix fun AbstractQueryBuilder.from(
    table: String,
) = also { this.from = From.Table(table) }

inline infix fun AbstractQueryBuilder.from(
    block: AbstractQueryBuilder.() -> From,
): AbstractQueryBuilder {
    this.from = block()
    return this
}

inline fun AbstractQueryBuilder.join(
    other: From.Table,
    block: From.Join.Partial.() -> From.Join,
) = let {
    val joinResult = requireNotNull(from) {
        "`from` has not been set on your query yet"
    }.join(other)
    block(joinResult)
}

inline fun AbstractQueryBuilder.join(
    other: String,
    block: From.Join.Partial.() -> From.Join,
) = join(other.asTable(), block)

inline fun AbstractQueryBuilder.innerJoin(
    other: From.Table,
    block: From.Join.Partial.() -> From.Join,
) = let {
    val joinResult = requireNotNull(from) {
        "`from` has not been set on your query yet"
    }.innerJoin(other)
    block(joinResult)
}

inline fun AbstractQueryBuilder.innerJoin(
    other: String,
    block: From.Join.Partial.() -> From.Join,
) = innerJoin(other.asTable(), block)

inline fun AbstractQueryBuilder.leftJoin(
    other: From.Table,
    block: From.Join.Partial.() -> From.Join,
) = let {
    val joinResult = requireNotNull(from) {
        "`from` has not been set on your query yet"
    }.leftJoin(other)
    block(joinResult)
}

inline fun AbstractQueryBuilder.leftJoin(
    other: String,
    block: From.Join.Partial.() -> From.Join,
) = leftJoin(other.asTable(), block)

inline fun AbstractQueryBuilder.crossJoin(
    other: From.Table,
    block: From.Join.Partial.() -> From.Join,
) = let {
    val joinResult = requireNotNull(from) {
        "`from` has not been set on your query yet"
    }.crossJoin(other)
    block(joinResult)
}

inline fun AbstractQueryBuilder.crossJoin(
    other: String,
    block: From.Join.Partial.() -> From.Join,
) = crossJoin(other.asTable(), block)

inline infix fun AbstractQueryBuilder.where(
    block: AbstractQueryBuilder.() -> Criteria,
): AbstractQueryBuilder {
    this.criteria = block()
    return this
}

/**
 * Applied AND operator with a when statement, this is useful when chaining multiple WHERE clauses
 */
inline infix fun AbstractQueryBuilder.whereAnd(
    block: AbstractQueryBuilder.() -> Criteria,
): AbstractQueryBuilder {
    criteria = when {
        criteria != null -> criteria and block()
        else -> block()
    }
    return this
}

/**
 * Applied OR operator with a when statement, this is useful when chaining multiple WHERE clauses
 */
inline infix fun AbstractQueryBuilder.whereOr(
    block: AbstractQueryBuilder.() -> Criteria,
): AbstractQueryBuilder {
    criteria = when {
        criteria != null -> criteria or block()
        else -> block()
    }
    return this
}

infix fun AbstractQueryBuilder.groupBy(
    projections: List<Projection>,
) = also { this.groupBy.addAll(projections) }

infix fun AbstractQueryBuilder.groupBy(
    column: Projection,
) = also { this.groupBy.add(column) }

fun AbstractQueryBuilder.clearGroupBy() = also {
    this.groupBy.clear()
}

infix fun AbstractQueryBuilder.orderByAsc(
    column: Projection,
) = also { this.orderBy.add(column.orderAsc(false)) }

/**
 * Order by asc case insensitive
 */
infix fun AbstractQueryBuilder.orderByAscCollate(
    column: Projection,
) = also { this.orderBy.add(column.orderAsc(true)) }

infix fun AbstractQueryBuilder.orderByDesc(
    column: Projection,
) = also { this.orderBy.add(column.orderDesc(false)) }

/**
 * Order by desc case insensitive
 */
infix fun AbstractQueryBuilder.orderByDescCollate(
    column: Projection,
) = also { this.orderBy.add(column.orderDesc(true)) }

fun AbstractQueryBuilder.clearOrderBy() = also {
    this.orderBy.clear()
}

infix fun AbstractQueryBuilder.skip(skip: Int) = also {
    this.skip = skip
}

infix fun AbstractQueryBuilder.take(take: Int) = also {
    this.skip = take
}

infix fun AbstractQueryBuilder.distinct(distinct: Boolean) = also {
    this.distinct = distinct
}

infix fun AbstractQueryBuilder.union(query: AbstractQueryBuilder) = also {
    query.unionAll = false
    this.unionQueryBuilders.add(query)
}

infix fun AbstractQueryBuilder.unionAll(query: AbstractQueryBuilder) = also {
    query.unionAll = true
    this.unionQueryBuilders.add(query)
}
