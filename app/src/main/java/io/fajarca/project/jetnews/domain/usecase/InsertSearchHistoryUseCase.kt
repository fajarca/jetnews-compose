package io.fajarca.project.jetnews.domain.usecase

import io.fajarca.project.jetnews.domain.entity.SearchHistory
import io.fajarca.project.jetnews.domain.repository.SearchHistoryRepository
import java.util.*
import javax.inject.Inject

class InsertSearchHistoryUseCase @Inject constructor(private val repository: SearchHistoryRepository) {

    suspend fun execute(query: String) {
        repository.insert(SearchHistory(query = query, createdAt = Date()))
    }

}
