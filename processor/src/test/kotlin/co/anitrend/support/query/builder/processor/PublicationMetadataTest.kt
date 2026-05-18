package co.anitrend.support.query.builder.processor

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class PublicationMetadataTest {

    @Test
    fun `processor runtime metadata should include room common`() {
        val repoRoot =
            generateSequence(File(System.getProperty("user.dir")).absoluteFile) { it.parentFile }
                .firstOrNull { candidate ->
                    File(candidate, "settings.gradle.kts").isFile && File(candidate, "gradlew").isFile
                }
                ?: error("Unable to locate repository root from `${System.getProperty("user.dir")}`")

        val moduleFile = File(repoRoot, "processor/build/publications/maven/module.json")
        assertTrue(moduleFile.isFile, "Expected `${moduleFile.absolutePath}` to exist")

        val moduleText = moduleFile.readText()
        assertEquals(true, moduleText.contains("\"runtimeElements\""))
        assertTrue(
            moduleText.contains("\"group\": \"androidx.room\"") && moduleText.contains("\"module\": \"room-common\""),
            "Expected processor runtime metadata to publish androidx.room room-common. See `${moduleFile.absolutePath}`",
        )
    }
}
