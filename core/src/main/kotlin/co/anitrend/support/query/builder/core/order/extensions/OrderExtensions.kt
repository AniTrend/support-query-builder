package co.anitrend.support.query.builder.core.order.extensions

import co.anitrend.support.query.builder.core.order.Order
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.asColumn

infix fun Projection.orderAsc(ignoreCase: Boolean) =
    Order.Ascending(ignoreCase, this)

infix fun Projection.orderDesc(ignoreCase: Boolean) =
    Order.Descending(ignoreCase, this)

infix fun String.orderAsc(ignoreCase: Boolean) =
    asColumn().orderAsc(ignoreCase)

infix fun String.orderDesc(ignoreCase: Boolean) =
    asColumn().orderDesc(ignoreCase)