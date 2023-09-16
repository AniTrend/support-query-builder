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

package co.anitrend.support.query.builder.processor

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import co.anitrend.support.query.builder.annotation.EntitySchema
import co.anitrend.support.query.builder.processor.extensions.createCandidate
import co.anitrend.support.query.builder.processor.factory.ClassFactory
import co.anitrend.support.query.builder.processor.logger.CoreLogger
import co.anitrend.support.query.builder.processor.logger.contract.ILogger
import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

@AutoService(Processor::class)
class EntitySchemaProcessor : AbstractProcessor() {

    private lateinit var logger: ILogger
    private lateinit var types: Types
    private lateinit var elements: Elements

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        logger = CoreLogger(processingEnv.messager)
        types = processingEnv.typeUtils
        elements = processingEnv.elementUtils
        logger.lineBreakWithSeparatorCharacter()
    }

    override fun getSupportedAnnotationTypes() = setOf(
        EntitySchema::class.java.canonicalName,
        ColumnInfo::class.java.canonicalName,
        Embedded::class.java.canonicalName,
        Entity::class.java.canonicalName,
    )

    /**
     * If [EntitySchema] has any overrides we'd specify them here
     */
    override fun getSupportedOptions() = emptySet<String>()

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnvironment: RoundEnvironment,
    ): Boolean {
        val elementItems = roundEnvironment.getElementsAnnotatedWith(
            EntitySchema::class.java,
        ).map { element -> element.createCandidate(types, logger, roundEnvironment) }
        if (elementItems.isNotEmpty()) {
            logger.debug("Available candidates: [${elementItems.joinToString(separator = ", ")}]")
            ClassFactory(processingEnv, elements, logger).generateUsing(elementItems)
        }
        return true
    }
}
