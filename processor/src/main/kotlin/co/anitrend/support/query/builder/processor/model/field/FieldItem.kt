package co.anitrend.support.query.builder.processor.model.field

import javax.lang.model.element.Element

/**
 * A wrapper for fields that have a name and an annotation
 */
data class FieldItem<T : Annotation>(
    val element: Element,
    val annotation: T
)