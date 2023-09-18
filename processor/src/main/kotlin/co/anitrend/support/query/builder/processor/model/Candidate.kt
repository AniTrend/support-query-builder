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

package co.anitrend.support.query.builder.processor.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import co.anitrend.support.query.builder.processor.extensions.enclosingTypeOf
import co.anitrend.support.query.builder.processor.logger.contract.ILogger
import co.anitrend.support.query.builder.processor.model.column.ColumnItem
import co.anitrend.support.query.builder.processor.model.core.Item
import co.anitrend.support.query.builder.processor.model.embed.EmbedItem
import co.anitrend.support.query.builder.processor.model.table.TableItem
import javax.lang.model.element.Element
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

internal class Candidate(
    private val types: Types,
    private val logger: ILogger,
    private val element: Element,
) {
    private fun name(): String = element.simpleName.toString()
    fun packageName(elements: Elements) = elements.getPackageOf(element).toString()
    fun createFileName() = "${name()}Schema"

    private fun getColumns(element: Element): List<ColumnItem> {
        val fieldColumns = element.enclosingTypeOf(ColumnInfo::class.java)
        if (fieldColumns.isEmpty()) {
            logger.warning("$element does not contain any properties annotated with `androidx.room.ColumnInfo`")
        } else {
            logger.debug("Column names for $element as [${fieldColumns.joinToString(", ") { "`${it.annotation.name}`" }}]")
        }
        return fieldColumns.map {
            ColumnItem(it.annotation.name, it.element.simpleName.toString())
        }
    }

    private fun getEmbedded(): List<EmbedItem> {
        return element.enclosingTypeOf(Embedded::class.java).map { field ->
            logger.debug(
                "Embedded prefix for $element.`${field.element.simpleName}` as ${
                    "`${field.annotation.prefix}` of type ${field.element.asType()}"
                }",
            )
            // The embedded class type of the current field
            val parentElement = types.asElement(field.element.asType())

            EmbedItem(
                prefix = field.annotation.prefix,
                fieldName = field.element.simpleName.toString(),
                columns = getColumns(parentElement),
            )
        }
    }

    fun getTable(): Item {
        val entity = element.getAnnotation(Entity::class.java)

        val tableName = when {
            entity.tableName.isEmpty() -> {
                logger.debug("$element does not have `tableName` set, using class name instead")
                element.simpleName.toString()
            }
            else -> entity.tableName
        }

        logger.debug("Table name for $element will be displayed as `$tableName`")
        return TableItem(
            tableName,
            getColumns(element),
            getEmbedded(),
        )
    }

    /**
     * Returns a string representation of the object.
     */
    override fun toString(): String = element.toString()
}
