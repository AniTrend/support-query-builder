package co.anitrend.support.query.builder.core.projection.extensions

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.projection.Projection

fun String.asColumn(table: String? = null) = Projection.Column(this, table)
fun Array<out String>.asColumn(table: String? = null) = map { Projection.Column(it, table) }

fun Any?.asConstant() = Projection.Constant(this)

fun Projection.Column.min() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.MIN
    )

fun Projection.Column.max() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.MAX
    )

fun Projection.Column.average() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.AVG
    )

fun Projection.Column.count() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.COUNT
    )

fun Projection.Column.sum() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.SUM
    )

fun Projection.Column.total() =
    Projection.Aggregate(
        this,
        Projection.Aggregate.Function.TOTAL
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
    return if (this is Projection.Alias)
        this.removeAlias()
    else this
}