package co.anitrend.support.query.builder.sample.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import co.anitrend.support.query.builder.sample.data.entity.person.PersonEntity
import co.anitrend.support.query.builder.sample.data.entity.person.dao.PersonDao
import co.anitrend.support.query.builder.sample.data.entity.pet.PetEntity
import co.anitrend.support.query.builder.sample.data.entity.pet.dao.PetDao
import org.jetbrains.annotations.TestOnly

@Database(
    exportSchema = false,
    entities = [PersonEntity::class, PetEntity::class],
    version = 1
)
internal abstract class Store : RoomDatabase() {
    abstract fun pet(): PetDao
    abstract fun person(): PersonDao

    companion object {
        private val creationCallback = object : RoomDatabase.Callback() {
            /**
             * Called when the database is created for the first time. This is called after all the
             * tables are created.
             *
             * @param db The database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) = db.run {
                beginTransaction()
                try {
                    val petValues = ContentValues()
                    (1..20).forEach {
                        val peopleValues = ContentValues()
                        peopleValues.put("id", it)
                        peopleValues.put("first_name", "first_name_0$it")
                        peopleValues.put("last_name", "last_name_0$it")
                        peopleValues.put("city_name", "city_0$it")
                        peopleValues.put("city_region", "region_0$it")
                        peopleValues.put("city_country", "country_0$it")
                        insert("person", SQLiteDatabase.CONFLICT_IGNORE, peopleValues)

                        if (it < 8) {
                            petValues.put("id", it)
                            petValues.put("name", "name_0$it")
                            petValues.put("owner_id", it)
                            petValues.put("breed_group", "group_0$it")
                            petValues.put("breed_origin", "origin_0$it")
                            insert("pet", SQLiteDatabase.CONFLICT_IGNORE, petValues)
                        }
                    }
                    setTransactionSuccessful()
                } finally {
                    endTransaction()
                }
            }
        }

        @TestOnly
        fun create(context: Context) = Room.inMemoryDatabaseBuilder(
            context,
            Store::class.java
        ).addCallback(
            creationCallback
        ).allowMainThreadQueries().build()
    }
}