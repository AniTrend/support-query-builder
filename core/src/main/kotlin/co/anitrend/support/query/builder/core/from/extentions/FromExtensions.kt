package co.anitrend.support.query.builder.core.from.extentions

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.from.From
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun String.asTable() =
    From.Table(this)

fun IQueryBuilder.asSubQuery() =
    From.SubQuery(this)

infix fun IQueryBuilder.innerJoin(other: From) =
    From.Join.Partial(asSubQuery(), other, From.Join.Type.INNER)

infix fun From.Table.innerJoin(other: From.Table) =
    From.Join.Partial(this, other, From.Join.Type.INNER)

infix fun String.innerJoin(other: String) =
    asTable().innerJoin(other.asTable())

inline fun IQueryBuilder.innerJoin(
    other: From.Table,
    block: From.Join.Partial.() -> From
) = block(innerJoin(other))


infix fun IQueryBuilder.leftJoin(other: From) =
    From.Join.Partial(asSubQuery(), other, From.Join.Type.LEFT)

fun From.Table.leftJoin(other: From.Table) =
    From.Join.Partial(this, other, From.Join.Type.LEFT)

infix fun String.leftJoin(other: String) =
    asTable().leftJoin(other.asTable())

inline fun IQueryBuilder.leftJoin(
    other: From.Table,
    block: From.Join.Partial.() -> From
) = block(leftJoin(other))

infix fun IQueryBuilder.crossJoin(other: From) =
    From.Join.Partial(asSubQuery(), other, From.Join.Type.CROSS)

infix fun From.Table.crossJoin(other: From.Table) =
    From.Join.Partial(this, other, From.Join.Type.CROSS)

infix fun String.crossJoin(other: String) =
    asTable().crossJoin(other.asTable())

inline fun IQueryBuilder.crossJoin(
    other: From.Table,
    block: From.Join.Partial.() -> From
) = block(crossJoin(other))


infix fun From.Aliasable.`as`(name: String) = also { aliasAs(name) }