package com.sid.demoapp.corountines

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Title(val title: String, @PrimaryKey val id: Int = 0)

@Dao
interface TitleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTitle(title: Title)

    @Query("select * from Title where id = 0")
    fun loadTitle(): LiveData<Title>
}

@Database(entities = [Title::class], version = 1, exportSchema = false)
abstract class TitleDatabase : RoomDatabase() {
    abstract val titleDao: TitleDao
}

private lateinit var INSTANCE: TitleDatabase

fun getDatabase(context: Context): TitleDatabase {
    synchronized(TitleDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                    .databaseBuilder(
                            context.applicationContext,
                            TitleDatabase::class.java,
                            "titles_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

    return INSTANCE
}
