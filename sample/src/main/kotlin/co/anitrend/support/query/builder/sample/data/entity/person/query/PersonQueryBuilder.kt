package co.anitrend.support.query.builder.sample.data.entity.person.query

import co.anitrend.support.query.builder.core.QueryBuilder
import co.anitrend.support.query.builder.core.criteria.extensions.equal
import co.anitrend.support.query.builder.core.criteria.extensions.matches
import co.anitrend.support.query.builder.core.projection.extensions.asColumn
import co.anitrend.support.query.builder.dsl.*
import co.anitrend.support.query.builder.sample.data.entity.person.PersonEntity
import co.anitrend.support.query.builder.sample.data.entity.person.PersonEntitySchema
import co.anitrend.support.query.builder.sample.data.entity.person.dao.PersonDao

/**
 * @param query A query that is mirror from some ui filter state
 */
internal class PersonQueryBuilder(
    private val dao: PersonDao,
    private val query: Query
) {

    fun filter(): List<PersonEntity> {
        val builder = QueryBuilder()
        builder from PersonEntitySchema.tableName
        query.id?.also { builder.whereAnd { PersonEntitySchema.id.asColumn() equal it } }
        query.firstName?.also { builder.whereAnd { PersonEntitySchema.firstName.asColumn() matches it } }
        query.lastName?.also { builder.whereAnd { PersonEntitySchema.lastName.asColumn() matches it } }
        query.cityName?.also { builder.whereAnd { PersonEntitySchema.cityName.asColumn() equal it } }
        query.cityRegion?.also { builder.whereAnd { PersonEntitySchema.cityRegion.asColumn() equal it } }
        query.cityCountry?.also { builder.whereAnd { PersonEntitySchema.cityCountry.asColumn() equal it } }

        return dao.rawQuery(builder.asSupportSQLiteQuery())
    }

    data class Query(
        val id: Long? = null,
        val firstName: String? = null,
        val lastName: String? = null,
        val cityName: String? = null,
        val cityRegion: String? = null,
        val cityCountry: String? = null
    )
}