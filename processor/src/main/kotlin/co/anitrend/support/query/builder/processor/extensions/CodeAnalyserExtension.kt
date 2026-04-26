package co.anitrend.support.query.builder.processor.extensions

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSValueArgument
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

fun KSClassDeclaration.annotationArgOf(block: (KSValueArgument) -> Boolean): KSValueArgument? =
    annotations.flatMap { it.arguments }.firstOrNull(block)

fun KSDeclaration.annotationOf(clazz: KClass<*>): KSAnnotation? {
    return annotations.firstOrNull {
        it.shortName.getShortName() == clazz.java.simpleName
    }
}

fun KSAnnotation.annotationArgOf(property: KProperty1<*, String>): KSValueArgument? {
    return arguments.firstOrNull {
        it.name?.getShortName() == property.name
    }
}
