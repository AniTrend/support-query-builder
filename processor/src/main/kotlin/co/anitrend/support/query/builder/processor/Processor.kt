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

import co.anitrend.support.query.builder.annotation.EntitySchema
import co.anitrend.support.query.builder.processor.codegen.EntitySchemaCodeGenerator
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate

class Processor(
    codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>,
) : SymbolProcessor {

    private val entitySchemaCodeGenerator =
        EntitySchemaCodeGenerator(
            codeGenerator = codeGenerator,
            options = options,
            logger = logger,
        )

    /**
     * Called by Kotlin Symbol Processing to run the processing task.
     *
     * @param resolver provides [SymbolProcessor] with access to compiler details such as Symbols.
     * @return A list of deferred symbols that the processor can't process. Only symbols that can't be processed at this round should be returned. Symbols in compiled code (libraries) are always valid and are ignored if returned in the deferral list.
     */
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val schema = requireNotNull(EntitySchema::class.qualifiedName) { "Unable to resolve EntitySchema annotation" }

        val schemaSymbols = resolver.getSymbolsWithAnnotation(schema)

        // Only act on valid symbols for this round
        val (valid, invalid) = schemaSymbols.partition { it.validate() }

        valid
            .filterIsInstance<KSClassDeclaration>()
            .groupBy { it.parentDeclaration?.qualifiedName?.asString() }
            .values
            .forEach { classDeclarations ->
                entitySchemaCodeGenerator(
                    resolver = resolver,
                    classDeclarations = classDeclarations
                )
            }

        // Return the still-invalid to let KSP try again next round
        return invalid
    }
}
