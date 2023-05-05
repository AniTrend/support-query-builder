package co.anitrend.support.query.builder.processor

import co.anitrend.support.query.builder.annotation.EntitySchema
import co.anitrend.support.query.builder.processor.extensions.createCandidate
import co.anitrend.support.query.builder.processor.visitor.AnnotationVisitor
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

class KspSchemaProcessor(
    private val options: Map<String, String>,
    private val kotlinVersion: KotlinVersion,
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {
    private val annotationVisitor = AnnotationVisitor(codeGenerator, logger)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(
            EntitySchema::class.java.canonicalName
        ).forEach {
            it.createCandidate(resolver, codeGenerator, logger)
        }

        return emptyList()
    }
}
