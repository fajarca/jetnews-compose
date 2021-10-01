package io.fajarca.project.jetnews.domain.usecase.search_history

import io.fajarca.project.jetnews.domain.entity.SearchHistory
import io.fajarca.project.jetnews.domain.repository.SearchHistoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSearchHistoryUseCase @Inject constructor(private val repository: SearchHistoryRepository) {

    fun execute(): Flow<List<SearchHistory>> {
        return repository.findAll()
    }
}
