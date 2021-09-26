package io.fajarca.project.jetnews.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ArticleDao {
    @Query("SELECT * FROM top_headlines ORDER BY published_at DESC")
    abstract fun findAll(): PagingSource<Int, ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertAll(vararg articles: ArticleEntity)

    @Query("SELECT DISTINCT source FROM top_headlines")
    abstract fun findAllNewsSource(): Flow<List<String>>

    @Query("UPDATE top_headlines SET bookmark = NOT bookmark WHERE title = :title")
    abstract suspend fun toggleBookmark(title: String): Int
}