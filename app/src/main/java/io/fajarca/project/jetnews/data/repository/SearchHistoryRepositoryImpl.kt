package io.fajarca.project.jetnews.data.repository

import io.fajarca.project.jetnews.data.mapper.SearchHistoryEntityMapper
import io.fajarca.project.jetnews.data.source.SearchHistoryLocalDataSource
import io.fajarca.project.jetnews.domain.entity.SearchHistory
import io.fajarca.project.jetnews.domain.repository.SearchHistoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchHistoryRepositoryImpl @Inject constructor(
    private val mapper: SearchHistoryEntityMapper,
    private val localDataSource: SearchHistoryLocalDataSource
) : SearchHistoryRepository {

    override fun findAll(): Flow<List<SearchHistory>> {
        return localDataSource
            .findAll()
            .map { mapper.fromEntity(it) }
    }

    override suspend fun insert(searchHistory: SearchHistory) {
        localDataSource.insert(mapper.toEntity(searchHistory))
    }
}