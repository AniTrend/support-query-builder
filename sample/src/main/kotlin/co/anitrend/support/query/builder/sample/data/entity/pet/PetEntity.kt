package co.anitrend.support.query.builder.sample.data.entity.pet

import androidx.room.*
import co.anitrend.support.query.builder.annotation.EntitySchema
import co.anitrend.support.query.builder.sample.data.entity.person.PersonEntity

@EntitySchema
@Entity(
    tableName = "pet",
    indices = [
        Index(value = ["owner_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"]
        )
    ]
)
internal data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "owner_id") val owner: Long,
    @Embedded(prefix = "breed_") val breed: Breed,
) {
    data class Breed(
        @ColumnInfo(name = "group") val group: String,
        @ColumnInfo(name = "origin") val origin: String
    )

    // This should not be generated since it is not referenced in the entity
    data class SomeConstruct(
        @ColumnInfo(name = "id") val id: Long,
        @Embedded(prefix = "prob_") val property: Property
    ) {
        data class Property(
            @ColumnInfo(name = "option_a") val optionA: String
        )
    }
}