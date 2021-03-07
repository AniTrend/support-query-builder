package co.anitrend.support.query.builder.sample.data.entity.person.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import co.anitrend.support.query.builder.sample.data.entity.person.PersonEntity

@Dao
internal interface PersonDao {

    @Query("select count(id) from person")
    fun count(): Int

    @RawQuery fun rawQuery(
        query: SupportSQLiteQuery
    ): List<PersonEntity>
}