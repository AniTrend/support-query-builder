package co.anitrend.support.query.builder.core.criteria.extensions

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.*


infix fun Criteria?.and(criteria: Criteria?) = Criteria.And(this, criteria)
infix fun Criteria?.or(criteria: Criteria?) = Criteria.Or(this, criteria)

fun Projection.isNull(): Criteria {
    return Criteria.Operator(this, IS_NULL, null)
}

fun Projection.notIsNull(): Criteria {
    return Criteria.Operator(this, IS_NOT_NULL, null)
}

infix fun Projection.equal(value: Any): Criteria {
    return Criteria.Operator(this, EQUALS, value)
}

infix fun Projection.notEquals(value: Any): Criteria {
    return Criteria.Operator(this, NOT_EQUALS, value)
}

infix fun Projection.Column.greaterThan(value: Any): Criteria {
    return Criteria.Operator(this, GREATER, value)
}

infix fun Projection.Column.lesserThan(value: Any): Criteria {
    return Criteria.Operator(this, LESSER, value)
}

infix fun Projection.Column.greaterThanOrEqual(value: Any): Criteria {
    return Criteria.Operator(this, GREATER_OR_EQUALS, value)
}

infix fun Projection.Column.lesserThanOrEqual(value: Any): Criteria {
    return Criteria.Operator(this, LESSER_OR_EQUALS, value)
}

infix fun Projection.Column.match(value: Any): Criteria {
    return Criteria.Operator(this, MATCH, value)
}

infix fun String.match(value: Any): Criteria {
    return asColumn().match(value)
}


infix fun Projection.Column.startsWith(value: String): Criteria {
    return Criteria.Operator(this, LIKE, "$value%")
}

infix fun Projection.Column.notStartsWith(value: String): Criteria {
    return Criteria.Operator(this, NOT_LIKE, "$value%")
}

infix fun Projection.Column.endsWith(value: String): Criteria {
    return Criteria.Operator(this, LIKE, "%$value")
}

infix fun Projection.Column.notEndsWith(value: String): Criteria {
    return Criteria.Operator(this, NOT_LIKE, "%$value")
}

infix fun Projection.Column.contains(value: String): Criteria {
    return Criteria.Operator(this, LIKE, "%$value%")
}

infix fun Projection.Column.notContains(value: String): Criteria {
    return Criteria.Operator(this, NOT_LIKE, "%$value%")
}


fun Projection.Column.between(valueMin: Any, valueMax: Any): Criteria {
    return Criteria.Between(this, valueMin, valueMax)
}

fun Any.valueBetween(columnMin: Projection.Column, columnMax: Projection.Column): Criteria {
    return Criteria.ValueBetween(columnMin, columnMax, this)
}



fun IQueryBuilder.exists(): Criteria {
    return Criteria.Exists(this)
}

fun IQueryBuilder.notExists(): Criteria {
    return Criteria.NotExists(this)
}



infix fun Projection.Column.`in`(values: Collection<Any>): Criteria {
    return Criteria.Contains(this, values, true)
}

infix fun Projection.Column.notIn(values: Collection<Any>): Criteria {
    return Criteria.Contains(this, values, false)
}