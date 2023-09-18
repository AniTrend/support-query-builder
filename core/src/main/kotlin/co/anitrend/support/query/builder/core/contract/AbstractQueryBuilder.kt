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

package co.anitrend.support.query.builder.core.contract

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.from.From
import co.anitrend.support.query.builder.core.order.Order
import co.anitrend.support.query.builder.core.projection.Projection

abstract class AbstractQueryBuilder : IQueryBuilder {

    abstract val projections: MutableList<Projection>
    abstract var from: From?
    abstract var criteria: Criteria?
    abstract val groupBy: MutableList<Projection>
    abstract var orderBy: MutableList<Order>
    var skip: Int = -1
    var take: Int = -1
    var distinct: Boolean = false

    abstract val unionQueryBuilders: MutableList<IQueryBuilder>
    var unionAll: Boolean = false

    protected abstract fun buildSelectClause(builder: StringBuilder)
    protected abstract fun buildFromClause(builder: StringBuilder)
    protected abstract fun buildWhereClause(builder: StringBuilder)
    protected abstract fun buildGroupByClause(builder: StringBuilder)
    protected abstract fun buildUnionClause(builder: StringBuilder)
    protected abstract fun buildOrderByClause(builder: StringBuilder)
    protected abstract fun buildTakeClause(builder: StringBuilder)
    protected abstract fun buildSkipClause(builder: StringBuilder)
}
