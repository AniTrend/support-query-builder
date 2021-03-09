package co.anitrend.support.query.builder.core.from

import co.anitrend.support.query.builder.core.criteria.extensions.equal
import co.anitrend.support.query.builder.core.from.extentions.*
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.`as`
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase

class FromTest : TestCase() {

    private val column = mockk<Projection.Column>()

    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     */
    override fun setUp() {
        every { column.build() } returns "column_name"
        every { column.buildParameters() } returns emptyList()
    }

    fun `test aliasable table name`() {
        val expected = "table_name AS n"
        val alias = "table_name".asTable() `as` "n"
        val actual = alias.build()
        assertEquals(expected, actual)
    }

    fun `test from table`() {
        val expected = "table_name"
        val table = "table_name".asTable()
        val actual = table.build()
        assertEquals(expected, actual)
    }

    fun `test inner join table`() {
        val expected = "table_name INNER JOIN other_table_name"
        val join = "table_name" innerJoin "other_table_name"
        val actual = join.build()
        assertEquals(expected, actual)
    }

    fun `test left join table`() {
        val expected = "table_name LEFT JOIN other_table_name"
        val join = "table_name" leftJoin "other_table_name"
        val actual = join.build()
        assertEquals(expected, actual)
    }

    fun `test cross join table`() {
        val expected = "table_name CROSS JOIN other_table_name"
        val join = "table_name" crossJoin "other_table_name"
        val actual = join.build()
        assertEquals(expected, actual)
    }

    fun `test join table with alias`() {
        val expected = "table_name AS n INNER JOIN other_table_name AS o ON column_name = ?"
        val `n alias` = "table_name".asTable() `as` "n"
        val `o alias` = "other_table_name".asTable() `as` "o"
        val join = `n alias` innerJoin `o alias` on column.equal("jack")
        val actual = join.build()
        assertEquals(expected, actual)
    }
}