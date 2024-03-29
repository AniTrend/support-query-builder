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

package co.anitrend.support.query.builder.core.from

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.criteria.extensions.equal
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.asColumn

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
        private val criteria: Criteria,
    ) : From() {

        data class Partial(
            private val left: From,
            private val right: From,
            private val type: Type,
        ) : From() {

            /**
             * Creates a Join on the [criteria]
             *
             * __Usage:__
             * ```
             * "table_name".asTable() innerJoin "other_table_name".asTable() on column.equal("jack")
             * ```
             *
             * @return Join
             */
            infix fun on(criteria: Criteria) =
                Join(left, right, type, criteria)

            /**
             * Creates a Join on the [left] constraint and the [right] constraint
             *
             * __Usage:__
             * ```
             * "table_name".asTable().innerJoin("other_table_name".asTable()).on("column", "some_column")
             * ```
             *
             * @return on
             */
            fun on(left: String, right: String) =
                on(left.asColumn() equal right.asColumn())

            /**
             * Creates a Join on the [left] constraint and the [right] constraint
             *
             * __Usage:__
             * ```
             * "table_name".asTable().innerJoin("other_table_name".asTable()).on(column, some_column)
             * ```
             *
             * @return on
             */
            fun on(left: Projection.Column, right: Projection.Column) =
                on(left equal right)

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
            CROSS("CROSS JOIN"),
        }
    }

    data class SubQuery(
        private val subQuery: IQueryBuilder,
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
        private val table: String,
    ) : Aliasable() {
        override fun build(): String {
            return alias?.let {
                "$table AS $alias"
            } ?: table
        }
    }
}
