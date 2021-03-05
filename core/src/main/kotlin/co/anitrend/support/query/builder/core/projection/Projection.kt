package co.anitrend.support.query.builder.core.projection

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder

/**
 * General unary operator for projection types
 */
sealed class Projection : IQueryBuilder {

    /**
     * [Column](https://sqlite.org/syntax/result-column.html) builder
     */
    data class Column(
        private val column: String,
        private val table: String? = null
    ) : Projection() {

        override fun build(): String {
            var selector = ""
            if (!table.isNullOrBlank())
                selector += "${table}."
            if (column.isNotBlank())
                selector += column

            return selector
        }

        override fun buildParameters() = emptyList<Any>()
    }

    /**
     * [Aggregate](https://sqlite.org/lang_aggfunc.html) builder
     *
     * @param projection [Column] projection
     * @param function Aggregate function
     */
    data class Aggregate(
        private val projection: Column,
        private val function: Function
    ) : Projection() {

        override fun build(): String {
            val column = projection.build()
            return "${function.name}($column)"
        }

        override fun buildParameters() = projection.buildParameters()

        /** Supported aggregate types */
        enum class Function {
            MIN,
            MAX,
            SUM,
            AVG,
            TOTAL,
            COUNT
        }
    }

    /**
     * [Alias]() builder
     */
    data class Alias(
        private val projection: Projection,
        private val alias: String
    ) : Projection() {

        internal fun removeAlias(): Projection {
            var temp = projection
            if (temp is Alias)
                temp = temp.projection
            return temp
        }

        override fun build(): String {
            val definition = projection.build()
            return "$definition AS $alias"
        }

        override fun buildParameters() = projection.buildParameters()
    }

    data class Constant(
        private val constant: Any?
    ) : Projection() {

        override fun build(): String {
            return constant?.let { "?" } ?: "NULL"
        }

        override fun buildParameters() = constant?.let { listOf(constant) }.orEmpty()
    }

    /** Sub query projection */
    data class SubQuery(
        private val queryBuilder: IQueryBuilder
    ) : Projection() {

        override fun build(): String {
            return "(${queryBuilder.build()})"
        }

        override fun buildParameters() = queryBuilder.buildParameters()
    }
}