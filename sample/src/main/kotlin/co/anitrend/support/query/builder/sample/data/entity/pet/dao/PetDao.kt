package co.anitrend.support.query.builder.sample.data.entity.pet.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import co.anitrend.support.query.builder.sample.data.entity.pet.PetEntity

@Dao
internal interface PetDao {

    @Query("select count(id) from pet")
    fun count(): Int

    @RawQuery
    fun rawQuery(
        query: SupportSQLiteQuery
    ): List<PetEntity>
}