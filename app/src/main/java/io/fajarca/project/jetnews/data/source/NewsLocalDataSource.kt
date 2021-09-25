package io.fajarca.project.jetnews.data.source

import androidx.paging.PagingSource
import io.fajarca.project.jetnews.data.db.TopHeadlineDao
import io.fajarca.project.jetnews.data.db.TopHeadlineEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSource @Inject constructor(
    private val dao: TopHeadlineDao
) {

    fun findAll(): PagingSource<Int, TopHeadlineEntity> {
        return dao.findAll()
    }

    fun findAllNewsSource(): Flow<List<String>> {
        return dao.findAllNewsSource()
    }

    suspend fun toggleFavorite(title : String): Int {
        return dao.toggleFavorite(title)
    }
}