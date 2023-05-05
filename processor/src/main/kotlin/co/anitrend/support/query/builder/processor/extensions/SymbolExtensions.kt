package co.anitrend.support.query.builder.processor.extensions

import co.anitrend.support.query.builder.annotation.EntitySchema
import co.anitrend.support.query.builder.processor.model.KsCandidate
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSValueArgument


internal fun KSDeclaration.annotationsOf(qualifiedName: String): Sequence<KSAnnotation> {
    return annotations.filter {
        it.annotationType
            .resolve()
            .declaration
            .qualifiedName
            ?.asString() == qualifiedName
    }
}


internal fun KSAnnotation.argumentValue(name: String): KSValueArgument? {
    return arguments.find {
        it.name?.asString() == name
    }
}


internal fun KSAnnotated.createCandidate(
    resolver: Resolver,
    codeGenerator: CodeGenerator,
    logger: KSPLogger
): KsCandidate {
    val entitySchemas = resolver.getSymbolsWithAnnotation(EntitySchema::class.java.canonicalName)
    val entities = resolver.getSymbolsWithAnnotation("androidx.room.Entity")

    val difference = entitySchemas.toSet() - entities.toSet()

    if (difference.isNotEmpty()) {
        logger.error(
            "[${difference.joinToString(", ")}] annotated with " +
                    "'co.anitrend.support.query.builder.annotation.EntitySchema' " +
                    "but not annotated with 'androidx.room.Entity'"
        )
    }


    val declaration = resolver as? KSDeclaration ?: error("Unable to resolve declaration")
    return KsCandidate(declaration, codeGenerator, logger)
}