package co.anitrend.support.query.builder.processor.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import co.anitrend.support.query.builder.processor.extensions.annotationArgOf
import co.anitrend.support.query.builder.processor.extensions.annotationOf
import co.anitrend.support.query.builder.processor.model.column.ColumnItem
import co.anitrend.support.query.builder.processor.model.core.Item
import co.anitrend.support.query.builder.processor.model.embed.EmbedItem
import co.anitrend.support.query.builder.processor.model.table.TableItem
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

internal class Candidate(
    val classDeclaration: KSClassDeclaration,
    private val logger: KSPLogger,
) {
    val packageName: String = classDeclaration.packageName.asString()
    val className: String = classDeclaration.simpleName.asString()
    val fileName: String = "${className}Schema"

    private fun KSDeclaration.getColumn(): ColumnItem? {
        val columnInfo = annotationOf(ColumnInfo::class)
        if (columnInfo == null) {
            logger.warn("[KSCandidate] Column property `${simpleName.getShortName()}` does not have a column annotation")
            return null
        }
        logger.info("[KSCandidate] Column name for $classDeclaration as `${columnInfo.shortName.asString()}`")

        val columnName = columnInfo.arguments.find { argument ->
            argument.name?.getShortName() == ColumnInfo::name.name
        }?.value as String


        return ColumnItem(
            name = columnName,
            fieldName = simpleName.getShortName()
        )
    }

    private fun Sequence<KSPropertyDeclaration>.getEmbeddings(): List<EmbedItem> {
        return mapNotNull { propertyDeclaration ->
            val embeddedAnnotation = propertyDeclaration.annotationOf(Embedded::class)

            if (embeddedAnnotation == null) {
                logger.warn("[KSCandidate] Embedded property `${propertyDeclaration.simpleName.getShortName()}` does not have an embedded annotation")
                return@mapNotNull null
            }

            val argument = embeddedAnnotation.annotationArgOf(Embedded::prefix)

            val prefix = argument?.value as? String
            if (prefix == null) {
                logger.warn("[KSCandidate] Embedded property `${propertyDeclaration.simpleName.getShortName()}` does not have a prefix argument")
            } else {
                logger.info(
                    "[KSCandidate] Embedded prefix for `${argument.name}` as `${prefix}`}",
                )
            }

            val typeDeclaration: KSDeclaration? = propertyDeclaration.type.resolve().declaration
            if (typeDeclaration !is KSClassDeclaration) {
                logger.warn("[KSCandidate] Embedded property `${propertyDeclaration.simpleName.getShortName()}` type is not a class declaration")
                return@mapNotNull null
            }

            logger.info(
                "[KSCandidate] Embedded `${propertyDeclaration.simpleName.getShortName()}` with prefix '$prefix' and type `$typeDeclaration`"
            )

            val columns = typeDeclaration
                .getDeclaredProperties()
                .mapNotNull { property ->
                    logger.info("[KSCandidate] Inspecting property `${property.simpleName.getShortName()}`")
                    property.getColumn()
                }.toList()

            EmbedItem(
                prefix = prefix ?: "",
                fieldName = propertyDeclaration.simpleName.getShortName(),
                columns = columns,
            )
        }.toList()
    }

    fun getTable(): Item {
        val tableName = classDeclaration.annotationArgOf { valueArgument ->
            logger.info("[KSCandidate.getTable] Argument name: ${valueArgument.name?.getShortName()} should match ${Entity::tableName.name}")
            valueArgument.name?.getShortName() == Entity::tableName.name
        }.value as String

        logger.info("[KSCandidate] Table name for $classDeclaration will be displayed as `$tableName`")

        val columns = classDeclaration.getDeclaredProperties().mapNotNull { property ->
            logger.info("[KSCandidate] Inspecting property `${property.simpleName.getShortName()}`")
            property.getColumn()
        }.toList()

        val embeddings = classDeclaration.getDeclaredProperties().getEmbeddings()

        return TableItem(
            name = requireNotNull(tableName) { "[KSCandidate.getTable] Table name cannot be null" },
            columns = columns,
            embeddings = embeddings,
        )
    }

    override fun toString(): String = classDeclaration.simpleName.asString()
}
