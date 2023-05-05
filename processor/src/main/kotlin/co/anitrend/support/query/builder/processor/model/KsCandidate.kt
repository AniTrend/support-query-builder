package co.anitrend.support.query.builder.processor.model

import co.anitrend.support.query.builder.processor.extensions.annotationsOf
import co.anitrend.support.query.builder.processor.extensions.argumentValue
import co.anitrend.support.query.builder.processor.model.column.ColumnItem
import co.anitrend.support.query.builder.processor.model.core.Item
import co.anitrend.support.query.builder.processor.model.embed.EmbedItem
import co.anitrend.support.query.builder.processor.model.table.TableItem
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSDeclaration

internal class KsCandidate(
    private val declaration: KSDeclaration,
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    private fun allColumns(): List<ColumnItem> {
        return emptyList()
    }

    private fun allEmbedded(): List<EmbedItem> {
        val embedded = declaration.annotationsOf(
            "androidx.room.Embedded"
        ).map {
            logger.info(
                "Embedded prefix for ${declaration}.`${it.parent}` as ${
                    "`${it.argumentValue("prefix")}`"
                }",
                it
            )

            EmbedItem(
                prefix = it.argumentValue("prefix").toString(),
                fieldName = "",
                columns = allColumns()

            )
        }
        return emptyList()
    }

    fun table(): Item {
        val entityAnnotation = declaration.annotationsOf(
            "androidx.room.Entity"
        ).first()
        val tableNameMember = entityAnnotation.argumentValue("tableName")

        return TableItem(
            name = tableNameMember?.value.toString(),
            columns = allColumns(),
            embedded = allEmbedded()
        )
    }

    /**
     * Returns a string representation of the object.
     */
    override fun toString(): String {
        return "${declaration.packageName}"
    }
}