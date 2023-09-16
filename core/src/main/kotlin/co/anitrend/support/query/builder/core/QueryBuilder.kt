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

package co.anitrend.support.query.builder.core

import co.anitrend.support.query.builder.core.contract.AbstractQueryBuilder
import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.from.From
import co.anitrend.support.query.builder.core.order.Order
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.removeAliasIfExists

class QueryBuilder : AbstractQueryBuilder() {

    override val projections = mutableListOf<Projection>()
    override var from: From? = null
    override var criteria: Criteria? = null
    override val groupBy = mutableListOf<Projection>()
    override var orderBy = mutableListOf<Order>()
    override val unionQueryBuilders = mutableListOf<IQueryBuilder>()

    override fun buildParameters(): List<Any> {
        val selectClauseParameters: MutableList<Any> = ArrayList()
        var oldOrderBy: List<Order>
        var oldSkip: Int
        var oldTake: Int

        selectClauseParameters.addAll(projections.flatMap(Projection::buildParameters))

        criteria?.let { selectClauseParameters.addAll(it.buildParameters()) }

        from?.also { selectClauseParameters.addAll(it.buildParameters()) }

        selectClauseParameters.addAll(groupBy.flatMap(Projection::buildParameters))

        for (union in unionQueryBuilders) {
            union as AbstractQueryBuilder
            oldOrderBy = union.orderBy
            oldSkip = union.skip
            oldTake = union.take
            union.orderBy = ArrayList()
            union.skip = -1
            union.take = -1
            selectClauseParameters.addAll(
                union.buildParameters(),
            )
            union.orderBy = oldOrderBy
            union.skip = oldSkip
            union.take = oldTake
        }

        selectClauseParameters.addAll(orderBy.flatMap(Order::buildParameters))

        return selectClauseParameters
    }

    override fun buildSelectClause(builder: StringBuilder) {
        builder.append("SELECT ")

        if (distinct) builder.append("DISTINCT ")

        if (projections.isEmpty()) {
            builder.append("*")
        } else {
            projections.forEach {
                builder.append(it.build())
                builder.append(", ")
            }
            builder.setLength(builder.length - 2) // removes the ", " from the last entry
        }

        builder.append(" ")
    }

    override fun buildFromClause(builder: StringBuilder) {
        from?.also {
            builder.append("FROM ")
            builder.append(it.build())
            builder.append(" ")
        }
    }

    override fun buildWhereClause(builder: StringBuilder) {
        criteria?.also {
            builder.append("WHERE ")
            builder.append(it.build())
        }
    }

    override fun buildGroupByClause(builder: StringBuilder) {
        if (groupBy.isNotEmpty()) {
            builder.append(" GROUP BY ")
            groupBy.forEach {
                val projection = it.removeAliasIfExists()
                builder.append(projection.build())
                builder.append(", ")
            }
            builder.setLength(builder.length - 2) // removes the ", " from the last entry
        }
    }

    override fun buildUnionClause(builder: StringBuilder) {
        var oldOrderBy: List<Order?>
        var oldSkip: Int
        var oldTake: Int

        for (union in unionQueryBuilders) {
            union as AbstractQueryBuilder
            builder.append(if (union.unionAll) " UNION ALL " else " UNION ")
            oldOrderBy = union.orderBy
            oldSkip = union.skip
            oldTake = union.take
            union.orderBy = ArrayList()
            union.skip = -1
            union.take = -1
            builder.append(union.build())
            union.orderBy = oldOrderBy
            union.skip = oldSkip
            union.take = oldTake
        }
    }

    override fun buildOrderByClause(builder: StringBuilder) {
        if (orderBy.isNotEmpty()) {
            builder.append(" ORDER BY ")
            for (o in orderBy) {
                builder.append(o.build())
                builder.append(", ")
            }
            builder.setLength(builder.length - 2) // removes the ", " from the last entry
        }
    }

    override fun buildTakeClause(builder: StringBuilder) {
        if (take > 0) {
            builder.append(" LIMIT ")
            builder.append(take)
        }
    }

    override fun buildSkipClause(builder: StringBuilder) {
        if (skip > 0) {
            builder.append(" OFFSET ")
            builder.append(skip)
        }
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()
        buildSelectClause(stringBuilder)
        buildFromClause(stringBuilder)
        buildWhereClause(stringBuilder)
        buildGroupByClause(stringBuilder)
        buildUnionClause(stringBuilder)
        buildOrderByClause(stringBuilder)
        buildTakeClause(stringBuilder)
        buildSkipClause(stringBuilder)
        return stringBuilder.toString().trim()
    }
}
