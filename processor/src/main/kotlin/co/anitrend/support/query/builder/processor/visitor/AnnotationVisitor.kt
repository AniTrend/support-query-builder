package co.anitrend.support.query.builder.processor.visitor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSVisitorVoid

internal class AnnotationVisitor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : KSVisitorVoid() {

    override fun visitAnnotation(annotation: KSAnnotation, data: Unit) {
        logger.logging(message = "Discovered", symbol = annotation)
    }
}