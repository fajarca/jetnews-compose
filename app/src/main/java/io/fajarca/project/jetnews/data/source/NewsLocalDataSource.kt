package io.fajarca.project.jetnews.data.source

import androidx.paging.PagingSource
import io.fajarca.project.jetnews.data.db.ArticleDao
import io.fajarca.project.jetnews.data.db.ArticleEntity
import io.fajarca.project.jetnews.data.db.SearchHistoryDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSource @Inject constructor(
    private val dao: ArticleDao
) {

    fun findAll(): PagingSource<Int, ArticleEntity> {
        return dao.findAll()
    }

    fun findAllNewsSource(): Flow<List<String>> {
        return dao.findAllNewsSource()
    }

    suspend fun toggleBookmark(title : String): Int {
        return dao.toggleBookmark(title)
    }
}