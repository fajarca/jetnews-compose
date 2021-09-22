package io.fajarca.project.jetnews.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TopHeadlineEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topHeadlineDao(): TopHeadlineDao
}