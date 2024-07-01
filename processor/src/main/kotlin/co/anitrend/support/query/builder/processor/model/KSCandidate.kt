package co.anitrend.support.query.builder.processor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import co.anitrend.support.query.builder.processor.model.column.ColumnItem
import co.anitrend.support.query.builder.processor.model.core.Item
import co.anitrend.support.query.builder.processor.model.embed.EmbedItem
import co.anitrend.support.query.builder.processor.model.table.TableItem
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration

internal class KSCandidate(
    private val classDeclaration: KSClassDeclaration,
    private val logger: KSPLogger
) {
    val packageName: String = classDeclaration.packageName.asString()
    val className: String = classDeclaration.simpleName.asString()
    val fileName: String = "${className}Schema"

    private fun getColumns(): List<ColumnItem> {
        val fieldColumns = classDeclaration.annotations.filter {
            it.shortName.getQualifier() == ColumnInfo::class.java.simpleName
        }
        return fieldColumns.map { fieldColumn ->
            val columnName = fieldColumn.arguments.find { argument ->
                argument.name?.getShortName() == ColumnInfo::name.name
            }?.value as String
            ColumnItem(
                name = columnName,
                fieldName = fieldColumn.shortName.asString()
            )
        }.toList()
    }

    private fun getEmbedded(): List<EmbedItem> {
        return emptyList()
    }

    fun getTable(): Item {
        val entity = classDeclaration.annotations.find {
            it.shortName.getQualifier() == Entity::class.qualifiedName
        }

        val tableName = entity?.arguments?.find {
            it.name?.getShortName() == Entity::tableName.name
        }?.value as String

        return TableItem(
            name = tableName,
            columns = getColumns(),
            embedded = getEmbedded(),
        )
    }

    override fun toString(): String = classDeclaration.simpleName.asString()
}
