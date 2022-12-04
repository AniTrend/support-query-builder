package co.anitrend.support.query.builder.core.extensions

import co.anitrend.support.query.builder.core.contract.AbstractQueryBuilder
import java.math.BigDecimal


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
            is Float -> BigDecimal(toDouble()).stripTrailingZeros().toPlainString()
            is Double -> BigDecimal(this).stripTrailingZeros().toPlainString()
            else -> toString()
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