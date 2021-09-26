package io.fajarca.project.jetnews.data.mapper

import io.fajarca.project.jetnews.data.db.SearchHistoryEntity
import io.fajarca.project.jetnews.domain.entity.SearchHistory
import javax.inject.Inject

class SearchHistoryEntityMapper @Inject constructor() {

    fun toEntity(input: SearchHistory): SearchHistoryEntity {
        return SearchHistoryEntity(query = input.query, createdAt = input.createdAt)
    }

    fun fromEntity(input: List<SearchHistoryEntity>): List<SearchHistory> {
        return input.map { SearchHistory(it.id, it.query, it.createdAt) }
    }

}