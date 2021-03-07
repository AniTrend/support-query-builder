package co.anitrend.support.query.builder.processor.model.table

import co.anitrend.support.query.builder.processor.model.core.Item
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

internal data class TableItem(
    private val name: String,
    private val columns: List<Item>,
    private val embedded: List<Item>,
) : Item {
    override fun writeToBuilder(builder: TypeSpec.Builder) {
        builder.addProperty(
            PropertySpec.builder("tableName", String::class, KModifier.CONST)
                .initializer("%S", name)
                .build()
        )
        (columns + embedded).forEach { it.writeToBuilder(builder) }
    }

    /**
     * Returns a string representation of the object.
     */
    override fun toString(): String = name
}