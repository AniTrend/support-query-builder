package co.anitrend.support.query.builder.processor.extensions

import co.anitrend.support.query.builder.processor.Provider
import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.configureKsp
import com.tschuchort.compiletesting.sourcesGeneratedBySymbolProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.io.File

private const val KOTLIN_COMPILER_VERSION = "1.9"

@OptIn(ExperimentalCompilerApi::class)
fun SourceFile.compilation(
    temporaryFolder: File,
    useKsp2: Boolean = true,
) = KotlinCompilation().let { kotlinCompilation ->
    kotlinCompilation.workingDir = temporaryFolder
    kotlinCompilation.inheritClassPath = true
    kotlinCompilation.sources = listOf(this)
    kotlinCompilation.verbose = true
    kotlinCompilation.configureKsp(useKsp2 = useKsp2) {
        symbolProcessorProviders += Provider()
        incremental = true // The default now
        if (!useKsp2) {
            withCompilation = true // Only necessary for KSP1
            kotlinCompilation.languageVersion = KOTLIN_COMPILER_VERSION
        }
    }
    kotlinCompilation
}

@OptIn(ExperimentalCompilerApi::class)
fun JvmCompilationResult.generatedKotlinSources(): List<File> {
    return sourcesGeneratedBySymbolProcessor
        .filter { it.isFile && it.extension == "kt" }
        .toList()
}
