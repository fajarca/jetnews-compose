package io.fajarca.project.jetnews.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        ArticleEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1
)
@TypeConverters(DatabaseTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}