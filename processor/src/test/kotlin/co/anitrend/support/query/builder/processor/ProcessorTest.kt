package co.anitrend.support.query.builder.processor

import co.anitrend.support.query.builder.processor.util.template
import co.anitrend.support.query.builder.processor.util.verifyFailing
import co.anitrend.support.query.builder.processor.util.verifyPassing
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class ProcessorTest {

    @TempDir
    lateinit var temporaryFolder: File

    @OptIn(ExperimentalCompilerApi::class)
    @Test
    fun `should pass when entity is annotated correctly`() {
        verifyPassing(
            temporaryFolder = temporaryFolder,
            source = kotlin(
                name = "Person.kt",
                contents = """
                    package co.anitrend.support.query.builder.sample.data.entity.person

                    import androidx.room.ColumnInfo
                    import androidx.room.Embedded
                    import androidx.room.Entity
                    import androidx.room.PrimaryKey
                    import co.anitrend.support.query.builder.annotation.EntitySchema

                    @EntitySchema
                    @Entity(tableName = "person")
                    internal data class PersonEntity(
                        @PrimaryKey(autoGenerate = true)
                        @ColumnInfo(name = "id") val id: Long,
                        @ColumnInfo(name = "first_name") val firstName: String,
                        @ColumnInfo(name = "last_name") val lastName: String,
                        @Embedded(prefix = "city_") val city: City
                    ) {
                        data class City(
                            @ColumnInfo(name = "name") val name: String,
                            @ColumnInfo(name = "region") val region: String,
                            @ColumnInfo(name = "country") val country: String
                        )
                    }
                """.trimIndent()
            ),
            output = template(
                """
                    package co.anitrend.support.query.builder.sample.`data`.entity.person

                    import kotlin.String

                    public object PersonEntitySchema {
                      public const val tableName: String = "person"

                      public const val id: String = "id"

                      public const val firstName: String = "first_name"

                      public const val lastName: String = "last_name"

                      public const val cityName: String = "city_name"

                      public const val cityRegion: String = "city_region"

                      public const val cityCountry: String = "city_country"
                    }
                """.trimIndent()
            )
        )
    }

    @Test
    fun `should fail when entity is not annotated with anything`() {
        verifyFailing(
            temporaryFolder = temporaryFolder,
            source = kotlin(
                name = "Entity.kt",
                contents = """
                    package com.example

                    internal data class Entity(
                        val id: Long,
                        val firstName: String,
                        val lastName: String,
                    )
                """.trimIndent()
            ),
        )
    }
}
