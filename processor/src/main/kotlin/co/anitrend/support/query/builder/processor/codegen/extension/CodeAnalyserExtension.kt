package co.anitrend.support.query.builder.processor.codegen.extension

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSValueArgument

fun KSClassDeclaration.annotationArgOf(block: (KSValueArgument) -> Boolean) = annotations.flatMap { it.arguments }.first(block)
