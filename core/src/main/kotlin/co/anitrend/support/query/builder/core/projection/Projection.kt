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
        private val table: String? = null,
    ) : Projection() {

        override fun build(): String {
            var selector = ""
            if (!table.isNullOrBlank()) {
                selector += "$table."
            }
            if (column.isNotBlank()) {
                selector += column
            }

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
        private val function: Function,
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
            COUNT,
        }
    }

    /**
     * [Alias]() builder
     */
    data class Alias(
        private val projection: Projection,
        private val alias: String,
    ) : Projection() {

        internal fun removeAlias(): Projection {
            var temp = projection
            if (temp is Alias) {
                temp = temp.projection
            }
            return temp
        }

        override fun build(): String {
            val definition = projection.build()
            return "$definition AS $alias"
        }

        override fun buildParameters() = projection.buildParameters()
    }

    data class Constant(
        private val constant: Any?,
    ) : Projection() {

        override fun build(): String {
            return constant?.let { "?" } ?: "NULL"
        }

        override fun buildParameters() = constant?.let { listOf(constant) }.orEmpty()
    }

    /** Sub query projection */
    data class SubQuery(
        private val queryBuilder: IQueryBuilder,
    ) : Projection() {

        override fun build(): String {
            return "(${queryBuilder.build()})"
        }

        override fun buildParameters() = queryBuilder.buildParameters()
    }
}
