package co.anitrend.support.query.builder.processor

import co.anitrend.support.query.builder.processor.util.template
import co.anitrend.support.query.builder.processor.util.verifyFailing
import co.anitrend.support.query.builder.processor.util.verifyPassing
import co.anitrend.support.query.builder.processor.util.verifyPassingMulti
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

    @OptIn(ExperimentalCompilerApi::class)
    @Test
    fun `should use class name as table name when tableName is not explicitly set`() {
        verifyPassing(
            temporaryFolder = temporaryFolder,
            source = kotlin(
                name = "Animal.kt",
                contents = """
                    package com.example

                    import androidx.room.ColumnInfo
                    import androidx.room.Entity
                    import androidx.room.PrimaryKey
                    import co.anitrend.support.query.builder.annotation.EntitySchema

                    @EntitySchema
                    @Entity
                    internal data class AnimalEntity(
                        @PrimaryKey(autoGenerate = true)
                        @ColumnInfo(name = "id") val id: Long,
                        @ColumnInfo(name = "name") val name: String
                    )
                """.trimIndent()
            ),
            output = template(
                """
                    package com.example

                    import kotlin.String

                    public object AnimalEntitySchema {
                      public const val tableName: String = "AnimalEntity"

                      public const val id: String = "id"

                      public const val name: String = "name"
                    }
                """.trimIndent()
            )
        )
    }

    @OptIn(ExperimentalCompilerApi::class)
    @Test
    fun `should use class name as table name when tableName is set to empty string`() {
        verifyPassing(
            temporaryFolder = temporaryFolder,
            source = kotlin(
                name = "Plant.kt",
                contents = """
                    package com.example

                    import androidx.room.ColumnInfo
                    import androidx.room.Entity
                    import androidx.room.PrimaryKey
                    import co.anitrend.support.query.builder.annotation.EntitySchema

                    @EntitySchema
                    @Entity(tableName = "")
                    internal data class PlantEntity(
                        @PrimaryKey(autoGenerate = true)
                        @ColumnInfo(name = "id") val id: Long
                    )
                """.trimIndent()
            ),
            output = template(
                """
                    package com.example

                    import kotlin.String

                    public object PlantEntitySchema {
                      public const val tableName: String = "PlantEntity"

                      public const val id: String = "id"
                    }
                """.trimIndent()
            )
        )
    }

    @OptIn(ExperimentalCompilerApi::class)
    @Test
    fun `should use field name as column name when ColumnInfo name is not set`() {
        verifyPassing(
            temporaryFolder = temporaryFolder,
            source = kotlin(
                name = "Vehicle.kt",
                contents = """
                    package com.example

                    import androidx.room.ColumnInfo
                    import androidx.room.Entity
                    import androidx.room.PrimaryKey
                    import co.anitrend.support.query.builder.annotation.EntitySchema

                    @EntitySchema
                    @Entity(tableName = "vehicle")
                    internal data class VehicleEntity(
                        @PrimaryKey(autoGenerate = true)
                        @ColumnInfo val id: Long,
                        @ColumnInfo val brand: String
                    )
                """.trimIndent()
            ),
            output = template(
                """
                    package com.example

                    import kotlin.String

                    public object VehicleEntitySchema {
                      public const val tableName: String = "vehicle"

                      public const val id: String = "id"

                      public const val brand: String = "brand"
                    }
                """.trimIndent()
            )
        )
    }

    @OptIn(ExperimentalCompilerApi::class)
    @Test
    fun `should generate separate schemas for two entities in the same compilation`() {
        verifyPassingMulti(
            temporaryFolder = temporaryFolder,
            sources = listOf(
                kotlin(
                    name = "Dog.kt",
                    contents = """
                        package com.example

                        import androidx.room.ColumnInfo
                        import androidx.room.Entity
                        import androidx.room.PrimaryKey
                        import co.anitrend.support.query.builder.annotation.EntitySchema

                        @EntitySchema
                        @Entity(tableName = "dog")
                        internal data class DogEntity(
                            @PrimaryKey(autoGenerate = true)
                            @ColumnInfo(name = "id") val id: Long,
                            @ColumnInfo(name = "breed") val breed: String
                        )
                    """.trimIndent()
                ),
                kotlin(
                    name = "Cat.kt",
                    contents = """
                        package com.example

                        import androidx.room.ColumnInfo
                        import androidx.room.Entity
                        import androidx.room.PrimaryKey
                        import co.anitrend.support.query.builder.annotation.EntitySchema

                        @EntitySchema
                        @Entity(tableName = "cat")
                        internal data class CatEntity(
                            @PrimaryKey(autoGenerate = true)
                            @ColumnInfo(name = "id") val id: Long,
                            @ColumnInfo(name = "color") val color: String
                        )
                    """.trimIndent()
                ),
            ),
            expectedOutputs = mapOf(
                "DogEntitySchema" to template(
                    """
                        package com.example

                        import kotlin.String

                        public object DogEntitySchema {
                          public const val tableName: String = "dog"

                          public const val id: String = "id"

                          public const val breed: String = "breed"
                        }
                    """.trimIndent()
                ),
                "CatEntitySchema" to template(
                    """
                        package com.example

                        import kotlin.String

                        public object CatEntitySchema {
                          public const val tableName: String = "cat"

                          public const val id: String = "id"

                          public const val color: String = "color"
                        }
                    """.trimIndent()
                ),
            )
        )
    }

    @OptIn(ExperimentalCompilerApi::class)
    @Test
    fun `should use class name as table name when EntitySchema is used without Entity annotation`() {
        verifyPassing(
            temporaryFolder = temporaryFolder,
            source = kotlin(
                name = "Fruit.kt",
                contents = """
                    package com.example

                    import androidx.room.ColumnInfo
                    import co.anitrend.support.query.builder.annotation.EntitySchema

                    @EntitySchema
                    internal data class FruitEntity(
                        @ColumnInfo(name = "id") val id: Long,
                        @ColumnInfo(name = "name") val name: String
                    )
                """.trimIndent()
            ),
            output = template(
                """
                    package com.example

                    import kotlin.String

                    public object FruitEntitySchema {
                      public const val tableName: String = "FruitEntity"

                      public const val id: String = "id"

                      public const val name: String = "name"
                    }
                """.trimIndent()
            )
        )
    }

    @OptIn(ExperimentalCompilerApi::class)
    @Test
    fun `should emit embedded columns without prefix when Embedded prefix is empty`() {
        verifyPassing(
            temporaryFolder = temporaryFolder,
            source = kotlin(
                name = "Order.kt",
                contents = """
                    package com.example

                    import androidx.room.ColumnInfo
                    import androidx.room.Embedded
                    import androidx.room.Entity
                    import androidx.room.PrimaryKey
                    import co.anitrend.support.query.builder.annotation.EntitySchema

                    @EntitySchema
                    @Entity(tableName = "order")
                    internal data class OrderEntity(
                        @PrimaryKey(autoGenerate = true)
                        @ColumnInfo(name = "id") val id: Long,
                        @Embedded(prefix = "") val address: Address
                    ) {
                        data class Address(
                            @ColumnInfo(name = "street") val street: String,
                            @ColumnInfo(name = "city") val city: String
                        )
                    }
                """.trimIndent()
            ),
            output = template(
                """
                    package com.example

                    import kotlin.String

                    public object OrderEntitySchema {
                      public const val tableName: String = "order"

                      public const val id: String = "id"

                      public const val addressStreet: String = "street"

                      public const val addressCity: String = "city"
                    }
                """.trimIndent()
            )
        )
    }

    @OptIn(ExperimentalCompilerApi::class)
    @Test
    fun `should exclude PrimaryKey field from schema when it has no ColumnInfo annotation`() {
        verifyPassing(
            temporaryFolder = temporaryFolder,
            source = kotlin(
                name = "Tag.kt",
                contents = """
                    package com.example

                    import androidx.room.ColumnInfo
                    import androidx.room.Entity
                    import androidx.room.PrimaryKey
                    import co.anitrend.support.query.builder.annotation.EntitySchema

                    @EntitySchema
                    @Entity(tableName = "tag")
                    internal data class TagEntity(
                        @PrimaryKey(autoGenerate = true) val id: Long,
                        @ColumnInfo(name = "label") val label: String
                    )
                """.trimIndent()
            ),
            output = template(
                """
                    package com.example

                    import kotlin.String

                    public object TagEntitySchema {
                      public const val tableName: String = "tag"

                      public const val label: String = "label"
                    }
                """.trimIndent()
            )
        )
    }
}
