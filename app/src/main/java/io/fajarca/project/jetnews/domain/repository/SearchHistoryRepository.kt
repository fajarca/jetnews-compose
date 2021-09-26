package io.fajarca.project.jetnews.domain.repository

import io.fajarca.project.jetnews.domain.entity.SearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun findAll(): Flow<List<SearchHistory>>
    suspend fun insert(searchHistory : SearchHistory)
}