package io.fajarca.project.jetnews.domain.usecase.search_history

import io.fajarca.project.jetnews.domain.repository.SearchHistoryRepository
import javax.inject.Inject

class ClearSearchHistoryUseCase @Inject constructor(private val repository: SearchHistoryRepository) {

    suspend fun execute() {
        repository.deleteAllSearchHistory()
    }

}
