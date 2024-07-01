package co.anitrend.support.query.builder.processor.factory

import co.anitrend.support.query.builder.processor.model.Candidate
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.nio.file.FileAlreadyExistsException

internal class ClassFactory(
    private val codeGenerator: CodeGenerator,
    private val options: Map<String, String>,
    private val logger: KSPLogger,
) {

    // prevent duplicate writes across rounds
    private val emitted = mutableSetOf<String>() // key = "$pkg.$fileName"

    private fun TypeSpec.Builder.construct(item: Candidate): FileSpec {
        val typeSpec = build()
        logger.info("Created type spec:\n$typeSpec")
        return FileSpec.builder(item.packageName, item.fileName)
            .addType(typeSpec)
            .build()
    }

    private fun FileSpec.commitFrom(origin: KSClassDeclaration) {
        val key = "${packageName}.$name"
        if (!emitted.add(key)) {
            logger.info("[ClassFactory] Skipping duplicate emission of $key")
            return
        }

        logger.info(
            "Committing construct using options: ${
                options.entries.joinToString { "${it.key}=${it.value}" }
            }"
        )

        val sourceFile = origin.containingFile
        val deps = if (sourceFile != null) {
            // isolating, one output per source
            Dependencies(aggregating = false, sources = arrayOf(sourceFile))
        } else {
            // symbol came from classpath, fall back to aggregating
            Dependencies(aggregating = true)
        }

        try {
            codeGenerator.createNewFile(
                dependencies = deps,
                packageName = packageName,
                fileName = name,
            ).bufferedWriter().use { writer ->
                writeTo(writer)
            }
        } catch (e: FileAlreadyExistsException) {
            // Harmless in incremental/rounded runs
            logger.info("[ClassFactory] Already generated: $key")
        } catch (t: Throwable) {
            logger.warn("[ClassFactory] Failed to write $key: $t")
        }
    }

    private fun createTypeSpecBuilderWith(item: Candidate): TypeSpec.Builder {
        val builder = TypeSpec.objectBuilder(item.fileName)
        item.getTable().writeToBuilder(builder)
        return builder
    }

    fun generateUsing(items: List<Candidate>) {
        if (items.isEmpty()) {
            logger.info("[ClassFactory] No @EntitySchema candidates in this round")
            return
        }

        items.forEach { elementItem ->
            logger.info("[ClassFactory] Inspecting element `$elementItem` and preparing to generate object")
            val builder = runCatching { createTypeSpecBuilderWith(elementItem) }
                .onFailure { logger.warn("[ClassFactory] $it") }
                .getOrNull()

            // Use the declaration we came from to attach proper deps
            val origin = elementItem.classDeclaration
            builder?.construct(elementItem)?.commitFrom(origin)
        }
    }
}
