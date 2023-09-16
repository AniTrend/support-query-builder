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
        override val projection: Projection,
    ) : Order() {
        override val order: String = "ASC"
    }

    data class Descending(
        override val ignoreCase: Boolean,
        override val projection: Projection,
    ) : Order() {
        override val order: String = "DESC"
    }
}
