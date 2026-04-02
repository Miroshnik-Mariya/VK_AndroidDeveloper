package io.mmaltsev.vkeducation.data.appdetails.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AppDetailsEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDetailsDao(): AppDetailsDao

    companion object {
        const val DATABASE_NAME = "app_details_database"  
    }
}