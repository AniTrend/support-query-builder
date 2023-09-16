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
    private val columns: List<ColumnItem>,
) : Item {

    private fun writeForEachColumn(builder: TypeSpec.Builder) {
        columns.forEach { column ->
            val name = column.toString()
                .replaceFirstChar {
                    if (it.isLowerCase()) {
                        it.titlecase()
                    } else {
                        it.toString()
                    }
                }
            builder.addProperty(
                PropertySpec.builder("$fieldName$name", String::class, KModifier.CONST)
                    .initializer("%S", "$prefix$column")
                    .build(),
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
