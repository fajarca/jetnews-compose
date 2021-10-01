package io.fajarca.project.jetnews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.project.jetnews.data.db.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SearchHistoryDao {
    @Query("SELECT * FROM search_histories ORDER BY created_at DESC")
    abstract fun findAll(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(searchHistory: SearchHistoryEntity)

    @Query("DELETE FROM search_histories")
    abstract suspend fun deleteAll()
}