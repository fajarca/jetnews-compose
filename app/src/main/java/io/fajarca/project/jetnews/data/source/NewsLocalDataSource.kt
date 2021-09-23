package io.fajarca.project.jetnews.data.source

import io.fajarca.project.jetnews.data.db.TopHeadlineDao
import io.fajarca.project.jetnews.data.db.TopHeadlineEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class NewsLocalDataSource @Inject constructor(
    private val dao: TopHeadlineDao
) {

    fun findAll(): Flow<List<TopHeadlineEntity>> {
        return dao.findAll().distinctUntilChanged()
    }

    fun findAllNewsSource(): Flow<List<String>> {
        return dao.findAllNewsSource()
    }

    suspend fun insertAll(headlines : List<TopHeadlineEntity>) {
        dao.insertAll(*headlines.toTypedArray())
    }

}