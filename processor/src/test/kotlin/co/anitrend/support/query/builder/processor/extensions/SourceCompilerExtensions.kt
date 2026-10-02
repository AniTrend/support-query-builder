package co.anitrend.support.query.builder.processor.extensions

import co.anitrend.support.query.builder.processor.Provider
import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.configureKsp
import com.tschuchort.compiletesting.sourcesGeneratedBySymbolProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.io.File

@OptIn(ExperimentalCompilerApi::class)
fun SourceFile.compilation(
    temporaryFolder: File,
) = KotlinCompilation().let { kotlinCompilation ->
    kotlinCompilation.workingDir = temporaryFolder
    kotlinCompilation.inheritClassPath = true
    kotlinCompilation.sources = listOf(this)
    kotlinCompilation.verbose = true
    kotlinCompilation.configureKsp {
        symbolProcessorProviders += Provider()
        incremental = true // The default now
    }
    kotlinCompilation
}

@OptIn(ExperimentalCompilerApi::class)
fun List<SourceFile>.compilation(
    temporaryFolder: File,
) = KotlinCompilation().let { kotlinCompilation ->
    kotlinCompilation.workingDir = temporaryFolder
    kotlinCompilation.inheritClassPath = true
    kotlinCompilation.sources = this
    kotlinCompilation.verbose = true
    kotlinCompilation.configureKsp {
        symbolProcessorProviders += Provider()
        incremental = true
    }
    kotlinCompilation
}

@OptIn(ExperimentalCompilerApi::class)
fun JvmCompilationResult.generatedKotlinSources(): List<File> {
    return sourcesGeneratedBySymbolProcessor
        .filter { it.isFile && it.extension == "kt" }
        .toList()
}
