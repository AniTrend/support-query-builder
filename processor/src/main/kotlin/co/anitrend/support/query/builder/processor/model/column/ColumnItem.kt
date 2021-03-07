package co.anitrend.support.query.builder.processor.model.column

import co.anitrend.support.query.builder.processor.model.core.Item
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

internal data class ColumnItem(
    private val name: String,
    private val fieldName: String
) : Item {
    override fun writeToBuilder(builder: TypeSpec.Builder) {
        builder.addProperty(
            PropertySpec.builder(fieldName, String::class, KModifier.CONST)
                .initializer("%S", name)
                .build()
        )
    }

    /**
     * Returns a string representation of the object.
     */
    override fun toString(): String = name
}