package co.anitrend.support.query.builder.sample.data.entity.pet.query

import co.anitrend.support.query.builder.core.QueryBuilder
import co.anitrend.support.query.builder.core.criteria.extensions.equal
import co.anitrend.support.query.builder.core.criteria.extensions.match
import co.anitrend.support.query.builder.core.projection.extensions.asColumn
import co.anitrend.support.query.builder.dsl.asSupportSQLiteQuery
import co.anitrend.support.query.builder.dsl.from
import co.anitrend.support.query.builder.dsl.whereAnd
import co.anitrend.support.query.builder.sample.data.entity.pet.PetEntity
import co.anitrend.support.query.builder.sample.data.entity.pet.PetEntitySchema
import co.anitrend.support.query.builder.sample.data.entity.pet.dao.PetDao

/**
 * @param query A query that is mirror from some ui filter state
 */
internal class PetQueryBuilder(
    private val dao: PetDao,
    private val query: Query
) {
    fun filter(): List<PetEntity> {
        val builder = QueryBuilder()
        builder from PetEntitySchema.tableName
        query.id?.also { builder.whereAnd { PetEntitySchema.id.asColumn() equal it } }
        query.name?.also { builder.whereAnd { PetEntitySchema.name.asColumn() match it } }
        query.owner?.also { builder.whereAnd { PetEntitySchema.owner.asColumn() equal it } }
        query.breedType?.also { builder.whereAnd { PetEntitySchema.breedGroup.asColumn() equal it } }
        query.breedOrigin?.also { builder.whereAnd { PetEntitySchema.breedOrigin.asColumn() equal it } }

        return dao.rawQuery(builder.asSupportSQLiteQuery())
    }

    data class Query(
        val id: Long? = null,
        val name: String? = null,
        val owner: Long? = null,
        val breedType: String? = null,
        val breedOrigin: String? = null
    )
}