package co.anitrend.support.query.builder.core.order

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.projection.Projection

sealed class Order : IQueryBuilder {
    protected abstract val ignoreCase: Boolean
    protected abstract val projection: Projection
    protected abstract val order: String

    override fun build(): String {
        val column = projection.build()
        return when (ignoreCase) {
            true -> "$column COLLATE NOCASE $order"
            else -> "$column $order"
        }
    }

    override fun buildParameters() = projection.buildParameters()

    data class Ascending(
        override val ignoreCase: Boolean,
        override val projection: Projection.Column
    ) : Order() {
        override val order: String = "ASC"
    }

    data class Descending(
        override val ignoreCase: Boolean,
        override val projection: Projection.Column
    ) : Order() {
        override val order: String = "DESC"
    }
}