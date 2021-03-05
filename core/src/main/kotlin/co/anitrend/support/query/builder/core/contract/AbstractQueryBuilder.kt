package co.anitrend.support.query.builder.core.contract

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.from.From
import co.anitrend.support.query.builder.core.order.Order
import co.anitrend.support.query.builder.core.projection.Projection
import java.math.BigDecimal

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