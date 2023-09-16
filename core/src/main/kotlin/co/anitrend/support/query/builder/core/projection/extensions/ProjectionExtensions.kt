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

package co.anitrend.support.query.builder.core.projection.extensions

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.from.From
import co.anitrend.support.query.builder.core.projection.Projection

fun String.asColumn(table: String? = null) = Projection.Column(this, table)
fun String.asColumn(table: From.Table) = Projection.Column(this, table.build())
fun Array<out String>.asColumn(table: String? = null) = map { Projection.Column(it, table) }
fun Array<out String>.asColumn(table: From.Table) = map { Projection.Column(it, table.build()) }

fun Any?.asConstant() = Projection.Constant(this)

fun Projection.Column.min() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.MIN,
    )

fun Projection.Column.max() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.MAX,
    )

fun Projection.Column.average() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.AVG,
    )

fun Projection.Column.count() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.COUNT,
    )

fun Projection.Column.sum() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.SUM,
    )

fun Projection.Column.total() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.TOTAL,
    )

fun String.min() = asColumn().min()

fun String.max() = asColumn().max()

fun String.average() = asColumn().average()

fun String.count() = asColumn().count()

fun String.sum() = asColumn().sum()

fun String.total() = asColumn().total()

fun IQueryBuilder.asSubQuery() = Projection.SubQuery(this)

infix fun Projection.Column.`as`(alias: String): Projection {
    return Projection.Alias(this, alias)
}

internal fun Projection.removeAliasIfExists(): Projection {
    return if (this is Projection.Alias) {
        this.removeAlias()
    } else {
        this
    }
}
