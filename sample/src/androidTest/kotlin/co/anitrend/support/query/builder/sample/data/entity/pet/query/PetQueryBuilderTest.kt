package co.anitrend.support.query.builder.sample.data.entity.pet.query

import co.anitrend.support.query.builder.sample.common.CommonTest

internal class PetQueryBuilderTest : CommonTest() {
    private val dao by lazy {
        store.pet()
    }

    fun test_basic_pet_query() {
        val expected = 7
        val actual = dao.count()
        assertEquals(expected, actual)
    }

    fun test_basic_pet_filter_query() {
        val queryBuilder = PetQueryBuilder(dao, PetQueryBuilder.Query(owner = 1))
        val expected = 1
        val actual = queryBuilder.filter().size
        assertEquals(expected, actual)
    }
}