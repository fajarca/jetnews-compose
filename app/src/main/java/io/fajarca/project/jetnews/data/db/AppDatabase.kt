package io.fajarca.project.jetnews.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fajarca.project.jetnews.data.db.converter.DatabaseTypeConverter
import io.fajarca.project.jetnews.data.db.dao.ArticleDao
import io.fajarca.project.jetnews.data.db.dao.SearchHistoryDao
import io.fajarca.project.jetnews.data.db.entity.ArticleEntity
import io.fajarca.project.jetnews.data.db.entity.SearchHistoryEntity

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