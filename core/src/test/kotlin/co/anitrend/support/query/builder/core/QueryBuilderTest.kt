package co.anitrend.support.query.builder.core

import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.criteria.extensions.*
import co.anitrend.support.query.builder.core.from.From
import co.anitrend.support.query.builder.core.from.extentions.`as`
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.`as`
import co.anitrend.support.query.builder.core.projection.extensions.asColumn
import co.anitrend.support.query.builder.dsl.*
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase

class QueryBuilderTest : TestCase() {

    private val column = mockk<Projection.Column>()
    private val table = mockk<From.Table>()
    private val builder = QueryBuilder()

    public override fun setUp() {
        every { column.build() } returns "column_name"
        every { column.buildParameters() } returns emptyList()
        every { table.build() } returns "table_name"
        every { table.buildParameters() } returns emptyList()
    }

    fun `test general select statement`() {
        val expected = "SELECT * FROM table_name"
        val actual = builder
            .select("*")
            .from("table_name")
            .build()

        assertEquals(expected, actual)
    }

    fun `test general select statement without select clause`() {
        val expected = "SELECT * FROM table_name"
        val actual = builder
            .from("table_name")
            .build()

        assertEquals(expected, actual)
    }

    fun `test general select statement with alias on column`() {
        val alias = column `as` "t"

        val expected = "SELECT column_name AS t FROM table_name"
        val actual = builder
            .select(alias.build())
            .from("table_name")
            .build()

        assertEquals(expected, actual)
    }

    fun `test general select statement with alias on table`() {
        val alias = From.Table("table_name") `as` "n"
        val expected = "SELECT * FROM table_name AS n"
        val actual = builder
            .from(alias.build())
            .build()

        assertEquals(expected, actual)
    }

    fun `test select with where clause`() {
        val expected = "SELECT * FROM table_name WHERE column_name = 'something'"
        val query = builder
            .from(table)
            .where(
                "column_name".asColumn() equal "something"
            )

        val actual = query.asFullSqlString()

        assertEquals(expected, actual)
    }
}