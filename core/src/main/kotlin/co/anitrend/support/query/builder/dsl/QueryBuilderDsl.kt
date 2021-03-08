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
    projections: List<Projection>
) = also { this.projections.addAll(projections) }

fun AbstractQueryBuilder.select(
    vararg columns: String
) = select(columns.asColumn())

infix fun AbstractQueryBuilder.select(
    column: String
) = also { this.projections.add(column.asColumn()) }

infix fun AbstractQueryBuilder.select(
    column: Projection
) = also { this.projections.add(column) }

infix fun AbstractQueryBuilder.from(
    from: From
) = also { this.from = from }

infix fun AbstractQueryBuilder.from(
    subQuery: IQueryBuilder
) = also { this.from = From.SubQuery(subQuery) }

infix fun AbstractQueryBuilder.from(
    table: String
) = also { this.from = From.Table(table) }

inline infix fun AbstractQueryBuilder.where(
    block: AbstractQueryBuilder.() -> Criteria
): AbstractQueryBuilder {
    this.criteria = block()
    return this
}

/**
 * Applied AND operator with a when statement, this is useful when chaining multiple WHERE clauses
 */
inline infix fun AbstractQueryBuilder.whereAnd(
    block: AbstractQueryBuilder.() -> Criteria
): AbstractQueryBuilder {
    criteria = when {
        criteria != null -> criteria and block()
        else -> block()
    }
    return this
}

/**
 * Applied OR operator with a when statement, this is useful when chaining multiple WHERE clauses
 */
inline infix fun AbstractQueryBuilder.whereOr(
    block: AbstractQueryBuilder.() -> Criteria
): AbstractQueryBuilder {
    criteria = when {
        criteria != null -> criteria or block()
        else -> block()
    }
    return this
}

infix fun AbstractQueryBuilder.groupBy(
    projections: List<Projection>
) = also { this.groupBy.addAll(projections) }

infix fun AbstractQueryBuilder.groupBy(
    column: Projection
) = also { this.groupBy.add(column) }

fun AbstractQueryBuilder.clearGroupBy() = also {
    this.groupBy.clear()
}

infix fun AbstractQueryBuilder.orderByAsc(
    column: Projection
) = also { this.orderBy.add(column.orderAsc(false)) }

/**
 * Order by asc case insensitive
 */
infix fun AbstractQueryBuilder.orderByAscCollate(
    column: Projection
) = also { this.orderBy.add(column.orderAsc(true)) }

infix fun AbstractQueryBuilder.orderByDesc(
    column: Projection
) = also { this.orderBy.add(column.orderDesc(false)) }

/**
 * Order by desc case insensitive
 */
infix fun AbstractQueryBuilder.orderByDescCollate(
    column: Projection
) = also { this.orderBy.add(column.orderDesc(true)) }

fun AbstractQueryBuilder.clearOrderBy() = also {
    this.orderBy.clear()
}

infix fun AbstractQueryBuilder.skip(skip: Int) = also {
    this.skip = skip
}

infix fun AbstractQueryBuilder.take(take: Int) = also {
    this.skip = take
}

infix fun AbstractQueryBuilder.distinct(distinct: Boolean) = also {
    this.distinct = distinct
}

infix fun AbstractQueryBuilder.union(query: AbstractQueryBuilder) = also {
    query.unionAll = false
    this.unionQueryBuilders.add(query)
}

infix fun AbstractQueryBuilder.unionAll(query: AbstractQueryBuilder) = also {
    query.unionAll = true
    this.unionQueryBuilders.add(query)
}

fun AbstractQueryBuilder.asSupportSQLiteQuery() : SupportSQLiteQuery {
    return SimpleSQLiteQuery(build(), buildParameters().toTypedArray())
}


@TestOnly
internal fun AbstractQueryBuilder.asFullSqlString(): String {
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