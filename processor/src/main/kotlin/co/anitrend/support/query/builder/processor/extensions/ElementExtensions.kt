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

package co.anitrend.support.query.builder.processor.extensions

import androidx.room.Entity
import co.anitrend.support.query.builder.annotation.EntitySchema
import co.anitrend.support.query.builder.processor.logger.contract.ILogger
import co.anitrend.support.query.builder.processor.model.Candidate
import co.anitrend.support.query.builder.processor.model.field.FieldItem
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Types

/**
 * Creates a candidate by checking if it is also annotated with [androidx.room.Entity]
 */
internal fun Element.createCandidate(
    types: Types,
    logger: ILogger,
    roundEnvironment: RoundEnvironment,
): Candidate {
    val entitySchemas = roundEnvironment.getElementsAnnotatedWith(EntitySchema::class.java)
    val entities = roundEnvironment.getElementsAnnotatedWith(Entity::class.java)
    val difference = entitySchemas - entities
    if (difference.isNotEmpty()) {
        logger.error(
            "[${difference.joinToString(", ")}] annotated with " +
                "'co.anitrend.support.query.builder.annotation.EntitySchema' " +
                "but not annotated with 'androidx.room.Entity'",
        )
    }
    return Candidate(types, logger, this)
}

/**
 * Returns enclosed types on an elements by [definition]
 *
 * @see Element.getAnnotationsByType
 */
internal fun <T : Annotation> Element.enclosingTypeOf(definition: Class<T>): List<FieldItem<T>> {
    return enclosedElements.flatMap { element ->
        element.getAnnotationsByType(definition).map {
            FieldItem(element, it)
        }.toList()
    }
}

/**
 * Returns enclosed type on an elements that matches the [type] and [kind]
 *
 * @see Element.getEnclosedElements
 */
internal fun Element.enclosingTypeOf(type: TypeMirror, kind: ElementKind): Element {
    return enclosedElements.first { element ->
        element.asType() == type &&
            element.kind == kind
    }
}
