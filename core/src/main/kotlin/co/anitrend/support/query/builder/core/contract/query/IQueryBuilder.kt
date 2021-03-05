package co.anitrend.support.query.builder.core.contract.query

interface IQueryBuilder {
    fun build(): String
    fun buildParameters(): List<Any>
}