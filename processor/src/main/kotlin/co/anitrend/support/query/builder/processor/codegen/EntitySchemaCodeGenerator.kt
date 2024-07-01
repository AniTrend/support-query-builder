package co.anitrend.support.query.builder.processor.codegen

import co.anitrend.support.query.builder.processor.codegen.contract.ICodeGenerator
import co.anitrend.support.query.builder.processor.model.KSCandidate
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration

class EntitySchemaCodeGenerator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : ICodeGenerator {
    override fun invoke(classes: List<KSClassDeclaration>) {
        val candidates = classes.map {
            KSCandidate(
                classDeclaration = it,
                logger = logger
            )
        }

    }
}
