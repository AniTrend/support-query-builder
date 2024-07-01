package co.anitrend.support.query.builder.processor.util

import co.anitrend.support.query.builder.processor.extensions.compilation
import co.anitrend.support.query.builder.processor.extensions.generatedKotlinSources
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Assertions
import java.io.File

fun template(@Language("kotlin") content: String) = content.trimIndent()

@OptIn(ExperimentalCompilerApi::class)
fun verifyPassing(
    temporaryFolder: File,
    source: SourceFile,
    @Language("kotlin") output: String
) {
    val result = source.compilation(
        temporaryFolder = temporaryFolder,
    ).compile()

    Assertions.assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

    val generatedFiles = result.generatedKotlinSources()
    Assertions.assertTrue(
        generatedFiles.isNotEmpty(),
        "`generatedFiles` cannot be empty, make sure that files are being written"
    )

    val generatedFile = generatedFiles.find { it.name.contains("EntitySchema") }
    Assertions.assertNotNull(
        generatedFile,
        "No file matching `*EntitySchema.kt` exists in `generatedFiles`"
    )

    Assertions.assertEquals(output, generatedFile?.readText()?.trim())
}

@OptIn(ExperimentalCompilerApi::class)
fun verifyFailing(
    temporaryFolder: File,
    source: SourceFile,
) {
    val result = source.compilation(
        temporaryFolder = temporaryFolder,
    ).compile()

    Assertions.assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

    val generatedFiles = result.generatedKotlinSources()
    Assertions.assertTrue(
        generatedFiles.isEmpty(),
        "`generatedFiles` should be empty"
    )
}
