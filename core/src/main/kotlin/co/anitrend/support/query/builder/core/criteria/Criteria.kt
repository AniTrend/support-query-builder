package co.anitrend.support.query.builder.core.criteria

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.removeAliasIfExists

sealed class Criteria : IQueryBuilder {

    data class And(
        private val left: Criteria?,
        private val right: Criteria?
    ) : Criteria() {
        override fun build(): String {
            val expression = "(${left?.build()} AND ${right?.build()})"
            return expression.trim()
        }

        override fun buildParameters() =
            left?.buildParameters().orEmpty() +
                right?.buildParameters().orEmpty()
    }

    data class Between(
        private val projection: Projection,
        private val start: Any,
        private val end: Any
    ) : Criteria() {
        override fun build(): String {
            val stringBuilder = StringBuilder(
                projection.removeAliasIfExists().build()
            )
            stringBuilder.append(" BETWEEN ")
                .append("?")
                .append(" AND ")
                .append("?")
            return stringBuilder.toString()
        }

        override fun buildParameters() =
            projection.buildParameters() +
            listOf(start, end)
    }

    data class Exists(
        private val subQueryBuilder: IQueryBuilder
    ) : Criteria() {
        override fun build(): String {
            return "EXISTS(${subQueryBuilder.build()})"
        }

        override fun buildParameters() = subQueryBuilder.buildParameters()
    }

    data class NotExists(
        private val subQueryBuilder: IQueryBuilder
    ) : Criteria() {
        override fun build(): String {
            return "NOT EXISTS(${subQueryBuilder.build()})"
        }

        override fun buildParameters() = subQueryBuilder.buildParameters()
    }

    /**
     * Alias for `in` and `not in` which is controlled by [include]
     */
    data class Contains<T : Any>(
        private val projection: Projection,
        private val values: Collection<T>,
        private val include: Boolean
    ) : Criteria() {
        override fun build(): String {
            require(values.isNotEmpty()) {
                "values cannot be empty"
            }
            val stringBuilder = StringBuilder(
                projection.removeAliasIfExists().build()
            )
            if (include)
                stringBuilder.append(" IN (")
            else
                stringBuilder.append(" NOT IN (")
            repeat(values.size) {
                stringBuilder.append("?, ")
            }
            val size = stringBuilder.length
            /** Remove the last ', ' */
            stringBuilder.setLength(size - 2)
            stringBuilder.append(")")
            return stringBuilder.toString()
        }

        override fun buildParameters() =
            projection.buildParameters() + values
    }

    data class Or(
        private val left: Criteria?,
        private val right: Criteria?
    ) : Criteria() {
        override fun build(): String {
            val expression = "(${left?.build()} OR ${right?.build()})"
            return expression.trim()
        }

        override fun buildParameters() =
            left?.buildParameters().orEmpty() +
                right?.buildParameters().orEmpty()
    }

    data class Operator<T: Any>(
        private val projection: Projection,
        private val operator: Type = Type.EQUALS,
        private val value: T? = null
    ) : Criteria() {

        private fun createOperator(): Type {
            return if (value == null) {
                when (operator) {
                    Type.LIKE,
                    Type.EQUALS,
                    Type.IS_NULL -> Type.IS_NULL
                    else -> Type.IS_NOT_NULL
                }
            } else operator
        }

        override fun build(): String {
            val stringBuilder = StringBuilder(
                projection.removeAliasIfExists().build()
            )
            stringBuilder.append(" ${createOperator().actual} ")
            value?.also {
                when (it) {
                    is Projection.Alias -> stringBuilder.append(it.removeAlias())
                    is Projection -> stringBuilder.append(it.build())
                    else -> stringBuilder.append("?")
                }
            }
            return stringBuilder.toString()
        }

        override fun buildParameters() =
             projection.buildParameters() +
                 value?.let { listOf(it) }.orEmpty()

        enum class Type(val actual: String) {
            IS_NULL("IS NULL"),
            IS_NOT_NULL("IS NOT NULL"),
            EQUALS("="),
            NOT_EQUALS("<>"),
            GREATER(">"),
            LESSER("<"),
            GREATER_OR_EQUALS(">="),
            LESSER_OR_EQUALS("<="),
            MATCH("MATCH"),
            LIKE("LIKE"),
            NOT_LIKE("NOT LIKE")
        }
    }

    data class ValueBetween<T: Any>(
        private val start: Projection,
        private val end: Projection,
        private val value: T
    ) : Criteria() {
        override fun build(): String {
            val stringBuilder = StringBuilder("?")
            stringBuilder.append(" BETWEEN ")
                .append(start.removeAliasIfExists().build())
                .append(" AND ")
                .append(end.removeAliasIfExists().build())

            return stringBuilder.toString()
        }

        override fun buildParameters() =
            start.buildParameters() +
                end.buildParameters() +
                listOf(value)
    }
}