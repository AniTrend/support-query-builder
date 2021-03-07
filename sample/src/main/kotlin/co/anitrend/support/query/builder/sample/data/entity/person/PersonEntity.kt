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