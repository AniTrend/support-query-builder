package co.anitrend.support.query.builder.processor.provider

import co.anitrend.support.query.builder.processor.KspSchemaProcessor
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class SchemaProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return KspSchemaProcessor(
            options = environment.options,
            kotlinVersion = environment.kotlinVersion,
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
        )
    }
}