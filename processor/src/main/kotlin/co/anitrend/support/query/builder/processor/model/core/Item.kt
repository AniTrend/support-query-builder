package co.anitrend.support.query.builder.processor.model.core

import com.squareup.kotlinpoet.TypeSpec

internal interface Item {
    fun writeToBuilder(builder: TypeSpec.Builder)
}