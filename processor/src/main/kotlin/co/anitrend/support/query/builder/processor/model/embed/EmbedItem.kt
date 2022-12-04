package co.anitrend.support.query.builder.processor.model.embed

import co.anitrend.support.query.builder.processor.model.column.ColumnItem
import co.anitrend.support.query.builder.processor.model.core.Item
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.util.*

internal data class EmbedItem(
    private val prefix: String,
    private val fieldName: String,
    private val columns: List<ColumnItem>
) : Item {

    private fun writeForEachColumn(builder: TypeSpec.Builder) {
        columns.forEach { column ->
            val name = column.toString().uppercase()
            builder.addProperty(
                PropertySpec.builder("$fieldName$name", String::class, KModifier.CONST)
                    .initializer("%S", "$prefix$column")
                    .build()
            )
        }
    }

    override fun writeToBuilder(builder: TypeSpec.Builder) {
        writeForEachColumn(builder)
    }

    /**
     * Returns a string representation of the object.
     */
    override fun toString(): String = "$fieldName -> $prefix"
}