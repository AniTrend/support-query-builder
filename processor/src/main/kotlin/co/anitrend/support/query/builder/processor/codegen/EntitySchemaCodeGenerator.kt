package co.anitrend.support.query.builder.processor.codegen

import co.anitrend.support.query.builder.processor.codegen.contract.ICodeGenerator
import co.anitrend.support.query.builder.processor.factory.ClassFactory
import co.anitrend.support.query.builder.processor.model.Candidate
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration

class EntitySchemaCodeGenerator(
    private val codeGenerator: CodeGenerator,
    private val options: Map<String, String>,
    private val logger: KSPLogger,
) : ICodeGenerator {
    override fun invoke(resolver: Resolver, classDeclarations: List<KSClassDeclaration>) {
        val candidates = classDeclarations.map {
            val template = """
                Package name: ${it.packageName.asString()}
                Class name: ${it.simpleName.asString()}
                File name: ${it.simpleName.asString()}Schema
            """.trimIndent()
            logger.info("[EntitySchemaCodeGenerator] Inspecting class declaration: $template")
            Candidate(
                classDeclaration = it,
                logger = logger
            )
        }

        if (candidates.isEmpty()) {
            logger.info("[EntitySchemaCodeGenerator] No @EntitySchema candidates in this round")
            return
        }

        logger.info("[EntitySchemaCodeGenerator] Processed candidates: [${candidates.joinToString(separator = ", ")}]")
        val factory = ClassFactory(codeGenerator, options, logger)
        factory.generateUsing(candidates)
    }
}
