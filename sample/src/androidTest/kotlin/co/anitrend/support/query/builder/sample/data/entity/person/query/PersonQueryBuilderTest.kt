package co.anitrend.support.query.builder.sample.data.entity.person.query

import co.anitrend.support.query.builder.sample.common.CommonTest

internal class PersonQueryBuilderTest : CommonTest() {
    private val dao by lazy {
        store.person()
    }

    fun test_basic_person_query() {
        val expected = 20
        val actual = dao.count()
        assertEquals(expected, actual)
    }

    fun test_basic_person_filter_query() {
        val queryBuilder = PersonQueryBuilder(dao, PersonQueryBuilder.Query(id = 10))
        val expected = 1
        val actual = queryBuilder.filter().size
        assertEquals(expected, actual)
    }
}