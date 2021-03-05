package co.anitrend.support.query.builder.core.projection

import co.anitrend.support.query.builder.core.projection.extensions.`as`
import co.anitrend.support.query.builder.core.projection.extensions.asColumn
import co.anitrend.support.query.builder.core.projection.extensions.max
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase

class ProjectionTest : TestCase() {

    private val columnProjection = mockk<Projection.Column>()

    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     */
    override fun setUp() {
        every { columnProjection.build() } returns "column_name"
        every { columnProjection.buildParameters() } returns emptyList()
    }

    fun `test column projection without table name`() {
        val expected = "column_name"
        val actual = "column_name".asColumn().build()

        assertEquals(expected, actual)
    }

    fun `test column projection with table name`() {
        val expected = "table_name.column_name"
        val actual = "column_name".asColumn("table_name").build()

        assertEquals(expected, actual)
    }

    fun `test alias projection on column`() {
        val expected = "column_name AS n"
        val actual = (columnProjection `as` "n").build()

        assertEquals(expected, actual)
    }

    fun `test aggregate projection on column`() {
        val expected = "MAX(column_name)"
        val actual = columnProjection.max().build()

        assertEquals(expected, actual)
    }
}