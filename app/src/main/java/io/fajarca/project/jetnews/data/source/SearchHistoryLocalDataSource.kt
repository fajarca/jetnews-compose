package io.fajarca.project.jetnews.data.source

import io.fajarca.project.jetnews.data.db.dao.SearchHistoryDao
import io.fajarca.project.jetnews.data.db.entity.SearchHistoryEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchHistoryLocalDataSource @Inject constructor(
    private val dao: SearchHistoryDao
) {

    fun findAll(): Flow<List<SearchHistoryEntity>> {
        return dao.findAll()
    }

    suspend fun insert(entity: SearchHistoryEntity) {
         dao.insert(entity)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}