package co.anitrend.support.query.builder.core.criteria

import co.anitrend.support.query.builder.core.criteria.extensions.*
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.asColumn
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase

class CriteriaTest : TestCase() {

    private val columnName = mockk<Projection.Column>()
    private val columnLast = mockk<Projection.Column>()

    public override fun setUp() {
        every { columnName.build() } returns "column_name"
        every { columnName.buildParameters() } returns emptyList()
        every { columnLast.build() } returns "column_last"
        every { columnLast.buildParameters() } returns emptyList()
    }

    fun `test and criteria`() {
        val expected = "(column_name IS NULL AND column_last IS NOT NULL)"
        val criteria = columnName.isNull() and columnLast.notIsNull()
        val actual = criteria.build()

        assertEquals(expected, actual)
    }

    fun `test between criteria`() {
        val expected = "column_name BETWEEN ? AND ?"
        val criteria = columnName.between("yes", "no")
        val actual = criteria.build()

        assertEquals(expected, actual)
    }

    fun `test exists criteria`() {
        val expected = "EXISTS(column_name)"
        val criteria = columnName.exists()
        val actual = criteria.build()

        assertEquals(expected, actual)
    }

    fun `test not exists criteria`() {
        val expected = "NOT EXISTS(column_name)"
        val criteria = columnName.notExists()
        val actual = criteria.build()

        assertEquals(expected, actual)
    }

    fun `test contains using in collection criteria`() {
        val expected = "column_name IN (?, ?, ?)"
        val criteria = columnName `in` listOf("pink", "purple", "green")
        val actual = criteria.build()

        assertEquals(expected, actual)
    }

    fun `test contains using like criteria`() {
        val expected = "column_name LIKE ?"
        val criteria = columnName.contains("pink")
        val actual = criteria.build()

        assertEquals(expected, actual)
    }

    fun `test or criteria`() {
        val expected = "(column_name > ? OR column_last <= ?)"
        val criteria = columnName greaterThan 6 or columnLast.lesserThanOrEqual(4)
        val actual = criteria.build()

        assertEquals(expected, actual)
    }

    fun `test operator criteria`() {
        val expected = "column_name MATCH ?"
        val criteria = columnName.matches("pie")
        val actual = criteria.build()

        assertEquals(expected, actual)
    }

    fun `test value between criteria`() {
        val expected = "column_name BETWEEN ? AND ?"
        val criteria = columnName.between(9,0)
        val actual = criteria.build()

        assertEquals(expected, actual)
    }
}