package co.anitrend.support.query.builder.core

import co.anitrend.support.query.builder.core.criteria.extensions.and
import co.anitrend.support.query.builder.core.criteria.extensions.endsWith
import co.anitrend.support.query.builder.core.criteria.extensions.equal
import co.anitrend.support.query.builder.core.extensions.asFullSqlString
import co.anitrend.support.query.builder.core.from.From
import co.anitrend.support.query.builder.core.from.extentions.*
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.`as`
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
        val query = builder select "*" from "table_name"
        val actual = query.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test general select statement without select clause`() {
        val expected = "SELECT * FROM table_name"
        val query = builder from "table_name"
        val actual = query.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test general select statement with alias on column`() {
        val expected = "SELECT column_name AS t FROM table_name"
        val query = builder select (column `as` "t") from "table_name"
        val actual = query.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test general select statement with alias on table`() {
        val expected = "SELECT * FROM table_name AS n"
        val query = builder from ("table_name".asTable() `as` "n")
        val actual = query.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test select with where clause`() {
        val expected = "SELECT * FROM table_name WHERE column_name = 'something'"
        val query = builder from table where {
            column equal "something"
        }

        val actual = query.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test select with inner join clause`() {
        val expected = "SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id"
        builder from table.innerJoin("other_table_name") {
            on("other_column_id", "column_id")
        }
        val actual = builder.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test select with inner join and where clause`() {
        val expected = "SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id WHERE column_name = 'something'"
        builder from table.innerJoin("other_table_name".asTable()) {
            on("other_column_id", "column_id")
        } where { column equal "something" }
        val actual = builder.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test select with inner join, where and order by clause`() {
        val expected = "SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id WHERE column_name = 'something' ORDER BY column_name DESC"
        builder from table.innerJoin("other_table_name".asTable()) {
            on("other_column_id", "column_id")
        } where {
            column equal "something"
        } orderByDesc column
        val actual = builder.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test select with inner join, where, order by and group by clause`() {
        val expected = "SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id WHERE column_name = 'something' GROUP BY column_name ORDER BY column_name DESC"
        builder from table.innerJoin(
            "other_table_name".asTable()
        ).on(
            "other_column_id",
            "column_id"
        ) where {
            column equal "something"
        } orderByDesc column groupBy column
        val actual = builder.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test select with inner join and where clause plus filter`() {
        val expected = "SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id WHERE (column_name = 'something' AND column_name LIKE '%pe')"
        builder from {
            table.innerJoin("other_table_name").on(
                "other_column_id", "column_id"
            )
        } where {
            column.equal("something") and column.endsWith("pe")
        }
        val actual = builder.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test select with inner join, left join and where clause plus filter`() {
        val expected = "SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id LEFT JOIN some_table_name ON some_other_column_id = column_id WHERE (column_name = 'something' AND column_name LIKE '%pe')"
        builder from {
            table.innerJoin("other_table_name").on(
                "other_column_id", "column_id"
            )
            .leftJoin("some_table_name").
                on("some_other_column_id", "column_id")

        } where {
            (column equal "something") and (column endsWith "pe")
        }
        val actual = builder.asFullSqlString()

        assertEquals(expected, actual)
    }

    fun `test select with inner join, left join and where clause plus filter segmented`() {
        val expected = "SELECT * FROM table_name INNER JOIN other_table_name ON other_column_id = column_id LEFT JOIN some_table_name ON some_other_column_id = column_id WHERE (column_name = 'something' AND column_name LIKE '%pe')"
        builder from table
        builder from {
            innerJoin("other_table_name") {
                on("other_column_id", "column_id")
            }
        }
        builder from {
            leftJoin("some_table_name").on(
                "some_other_column_id", "column_id"
            )
        }
        builder where {
            (column equal "something") and (column endsWith "pe")
        }
        val actual = builder.asFullSqlString()

        assertEquals(expected, actual)
    }
}