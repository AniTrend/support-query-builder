/*
 * Copyright 2023 AniTrend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.anitrend.support.query.builder.core.criteria.extensions

import co.anitrend.support.query.builder.core.contract.query.IQueryBuilder
import co.anitrend.support.query.builder.core.criteria.Criteria
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.EQUALS
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.GREATER
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.GREATER_OR_EQUALS
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.IS_NOT_NULL
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.IS_NULL
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.LESSER
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.LESSER_OR_EQUALS
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.LIKE
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.MATCH
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.NOT_EQUALS
import co.anitrend.support.query.builder.core.criteria.Criteria.Operator.Type.NOT_LIKE
import co.anitrend.support.query.builder.core.projection.Projection
import co.anitrend.support.query.builder.core.projection.extensions.asColumn

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

infix fun Projection.notEqual(value: Any): Criteria {
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

infix fun String.equal(value: Any): Criteria {
    return asColumn().equal(value)
}

infix fun String.notEqual(value: Any): Criteria {
    return asColumn().notEqual(value)
}

infix fun String.greaterThan(value: Any): Criteria {
    return asColumn().greaterThan(value)
}

infix fun String.lesserThan(value: Any): Criteria {
    return asColumn().lesserThan(value)
}

infix fun String.greaterThanOrEqual(value: Any): Criteria {
    return asColumn().greaterThanOrEqual(value)
}

infix fun String.lesserThanOrEqual(value: Any): Criteria {
    return asColumn().lesserThanOrEqual(value)
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

infix fun Projection.Column.like(value: String): Criteria {
    return Criteria.Operator(this, LIKE, "%$value%")
}

infix fun Projection.Column.notLike(value: String): Criteria {
    return Criteria.Operator(this, NOT_LIKE, "%$value%")
}

fun Projection.Column.between(valueMin: Any, valueMax: Any): Criteria {
    return Criteria.Between(this, valueMin, valueMax)
}

infix fun String.startsWith(value: String): Criteria {
    return asColumn().startsWith("$value%")
}

infix fun String.notStartsWith(value: String): Criteria {
    return asColumn().notStartsWith("$value%")
}

infix fun String.endsWith(value: String): Criteria {
    return asColumn().endsWith("%$value")
}

infix fun String.notEndsWith(value: String): Criteria {
    return asColumn().notEndsWith("%$value")
}

infix fun String.like(value: String): Criteria {
    return asColumn().like("%$value%")
}

infix fun String.notLike(value: String): Criteria {
    return asColumn().notLike("%$value%")
}

fun String.between(valueMin: Any, valueMax: Any): Criteria {
    return asColumn().between(valueMin, valueMax)
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

infix fun String.`in`(values: Collection<Any>): Criteria {
    return asColumn().`in`(values)
}

infix fun String.notIn(values: Collection<Any>): Criteria {
    return asColumn().notIn(values)
}
