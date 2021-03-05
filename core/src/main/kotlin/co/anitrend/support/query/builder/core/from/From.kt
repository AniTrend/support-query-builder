package co.anitrend.support.query.builder.core.from

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.criteria.extensions.equal
import co.anitrend.support.query.builder.core.projection.Projection

sealed class From : IQueryBuilder {

    override fun buildParameters() = emptyList<Any>()

    @Suppress("SpellCheckingInspection")
    abstract class Aliasable : From() {
        protected var alias: String? = null
        fun aliasAs(name: String) {
            alias = name
        }
    }

    data class Join(
        private val left: From,
        private val right: From,
        private val type: Type,
        private val criteria: Criteria
    ) : From() {

        data class Partial(
            private val left: From,
            private val right: From,
            private val type: Type
        ) : From() {
            infix fun on(criteria: Criteria) =
                Join(left, right, type, criteria)

            infix fun Projection.Column.on(other: Projection.Column) =
                on(this equal other)

            override fun build(): String {
                return "${left.build()} ${type.actual} ${right.build()}"
            }
        }

        override fun build(): String {
            return "${left.build()} ${type.actual} ${right.build()} ON ${criteria.build()}"
        }

        override fun buildParameters() =
            left.buildParameters() +
            right.buildParameters() +
            criteria.buildParameters()

        enum class Type(val actual: String) {
            JOIN("JOIN"),
            INNER("INNER JOIN"),
            LEFT("LEFT JOIN"),
            CROSS("CROSS JOIN")
        }
    }

    data class SubQuery(
        private val subQuery: IQueryBuilder
    ) : Aliasable() {
        override fun build(): String {
            val query = "(${subQuery.build()})"
            return alias?.let {
                "$query AS $it"
            } ?: query
        }

        override fun buildParameters() = subQuery.buildParameters()
    }

    data class Table(
        private val table: String
    ) : Aliasable() {
        override fun build(): String {
            return alias?.let {
                "$table AS $alias"
            } ?: table
        }
    }
}