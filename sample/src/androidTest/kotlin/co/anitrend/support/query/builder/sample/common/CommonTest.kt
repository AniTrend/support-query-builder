package co.anitrend.support.query.builder.sample.common

import androidx.test.platform.app.InstrumentationRegistry
import co.anitrend.support.query.builder.sample.data.database.Store
import junit.framework.TestCase

internal abstract class CommonTest : TestCase() {
    protected val store by lazy {
        Store.create(InstrumentationRegistry.getInstrumentation().context)
    }
}