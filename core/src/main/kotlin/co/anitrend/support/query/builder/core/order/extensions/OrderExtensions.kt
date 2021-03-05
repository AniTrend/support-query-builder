package co.anitrend.support.query.builder.core.order.extensions

import co.anitrend.support.query.builder.core.order.Order
import co.anitrend.support.query.builder.core.projection.Projection

infix fun Projection.Column.orderAsc(ignoreCase: Boolean) =
    Order.Ascending(ignoreCase, this)

infix fun Projection.Column.orderDesc(ignoreCase: Boolean) =
    Order.Descending(ignoreCase, this)