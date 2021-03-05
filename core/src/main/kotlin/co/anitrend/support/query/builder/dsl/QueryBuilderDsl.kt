package co.anitrend.support.query.builder.dsl

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import co.anitrend.support.query.builder.core.contract.AbstractQueryBuilder
import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.criteria.extensions.and
import co.anitrend.support.query.builder.core.criteria.extensions.or
import co.anitrend.support.query.builder.core.from.From
import co.anitrend.support.query.builder.core.order.extensions.orderAsc
import co.anitrend.support.query.builder.core.order.extensions.orderDesc
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.asColumn
import org.jetbrains.annotations.TestOnly
import java.math.BigDecimal

fun AbstractQueryBuilder.select(
    projections: List<Projection.Column>
) = also { this.projections.addAll(projections) }

fun AbstractQueryBuilder.select(
    vararg columns: String
) = select(columns.asColumn())

fun AbstractQueryBuilder.from(
    from: From
) = also { this.from = from }

fun AbstractQueryBuilder.from(
    subQuery: IQueryBuilder
) = also { this.from = From.SubQuery(subQuery) }

fun AbstractQueryBuilder.from(
    table: String
) = also { this.from = From.Table(table) }

fun AbstractQueryBuilder.where(
    criteria: Criteria
) = also { this.criteria = criteria }

fun AbstractQueryBuilder.whereAnd(
    criteria: Criteria
) = also { this.criteria = this.criteria and criteria }

fun AbstractQueryBuilder.whereOr(
    criteria: Criteria
) = also { this.criteria = this.criteria or criteria }

fun AbstractQueryBuilder.groupBy(
    projections: List<Projection.Column>
) = also { this.projections.addAll(projections) }

fun AbstractQueryBuilder.groupBy(
    vararg columns: String
) = groupBy(columns.asColumn())

fun AbstractQueryBuilder.clearGroupBy() = also {
    this.groupBy.clear()
}

fun AbstractQueryBuilder.orderByAscending(
    projections: List<Projection.Column>, ignoreCase: Boolean
) = also { this.orderBy.addAll(projections.map { it.orderAsc(ignoreCase) }) }

fun AbstractQueryBuilder.orderByAscending(
    vararg columns: String, ignoreCase: Boolean
) = orderByAscending(columns.asColumn(), ignoreCase)

fun AbstractQueryBuilder.orderByDescending(
    projections: List<Projection.Column>, ignoreCase: Boolean
) = also { this.orderBy.addAll(projections.map { it.orderDesc(ignoreCase) }) }

fun AbstractQueryBuilder.orderByDescending(
    vararg columns: String, ignoreCase: Boolean
) = orderByDescending(columns.asColumn(), ignoreCase)

fun AbstractQueryBuilder.clearOrderBy() = also {
    this.orderBy.clear()
}

fun AbstractQueryBuilder.skip(skip: Int) = also {
    this.skip = skip
}

fun AbstractQueryBuilder.take(take: Int) = also {
    this.skip = take
}

fun AbstractQueryBuilder.distinct(distinct: Boolean) = also {
    this.distinct = distinct
}

fun AbstractQueryBuilder.union(query: AbstractQueryBuilder) = also {
    query.unionAll = false
    this.unionQueryBuilders.add(query)
}

fun AbstractQueryBuilder.unionAll(query: AbstractQueryBuilder) = also {
    query.unionAll = true
    this.unionQueryBuilders.add(query)
}

fun AbstractQueryBuilder.asSupportSQLiteQuery() : SupportSQLiteQuery {
    return SimpleSQLiteQuery(build(), buildParameters().toTypedArray())
}


@TestOnly
fun AbstractQueryBuilder.asFullSqlString(): String {
    fun appendEscapedSQLString(sb: StringBuilder, sqlString: String) {
        sb.append('\'')
        if (sqlString.indexOf('\'') != -1) {
            val length = sqlString.length
            for (i in 0 until length) {
                val c = sqlString[i]
                if (c == '\'') {
                    sb.append('\'')
                }
                sb.append(c)
            }
        } else sb.append(sqlString)
        sb.append('\'')
    }

    fun Any.toPlainString(): String {
        return when (this) {
            is String -> this
            is Float -> BigDecimal(this.toDouble()).stripTrailingZeros().toPlainString()
            is Double -> BigDecimal(this).stripTrailingZeros().toPlainString()
            else -> this.toString()
        }
    }

    val parameters = buildParameters()
    var sql = build()
    for (p in parameters) {
        val stringBuilder = StringBuilder()
        appendEscapedSQLString(stringBuilder, p.toPlainString())
        sql = sql.replaceFirst("\\?".toRegex(), stringBuilder.toString())
    }
    return sql
}