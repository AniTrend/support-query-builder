package co.anitrend.support.query.builder.core.order

import co.anitrend.support.query.builder.core.order.extensions.orderAsc
import co.anitrend.support.query.builder.core.order.extensions.orderDesc
import co.anitrend.support.query.builder.core.projection.Projection
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase

class OrderTest : TestCase() {
    private val projection = mockk<Projection.Column>()

    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     */
    override fun setUp() {
        every { projection.build() } returns "user_name"
        every { projection.buildParameters() } returns emptyList()
    }

    fun `test ascending order on column projection`() {
        val expected = " user_name ASC"
        val order = projection orderAsc false
        val actual = order.build()
        assertEquals(expected, actual)
    }

    fun `test descending order on column projection`() {
        val expected = " user_name DESC"
        val order = projection orderDesc false
        val actual = order.build()
        assertEquals(expected, actual)
    }

    fun `test ascending order on column projection with ignore case`() {
        val expected = " user_name COLLATE NOCASE ASC"
        val order = projection orderAsc true
        val actual = order.build()
        assertEquals(expected, actual)
    }

    fun `test descending order on column projection with ignore case`() {
        val expected = " user_name COLLATE NOCASE DESC"
        val order = projection orderDesc true
        val actual = order.build()
        assertEquals(expected, actual)
    }
}