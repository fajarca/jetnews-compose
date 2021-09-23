package io.fajarca.project.jetnews.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TopHeadlineDao {
    @Query("SELECT * FROM top_headlines")
    abstract fun findAll(): Flow<List<TopHeadlineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(vararg movie: TopHeadlineEntity)

    @Query("SELECT DISTINCT source FROM top_headlines")
    abstract fun findAllNewsSource(): Flow<List<String>>

    @Query("UPDATE top_headlines SET bookmark = NOT bookmark WHERE title = :title")
    abstract suspend fun toggleFavorite(title: String): Int
}