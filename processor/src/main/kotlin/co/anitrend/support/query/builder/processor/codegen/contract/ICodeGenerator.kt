package co.anitrend.support.query.builder.processor.codegen.contract

import com.google.devtools.ksp.symbol.KSClassDeclaration

interface ICodeGenerator {
    operator fun invoke(classes: List<KSClassDeclaration>)
}
