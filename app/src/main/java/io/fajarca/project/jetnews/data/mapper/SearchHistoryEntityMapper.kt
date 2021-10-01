package io.fajarca.project.jetnews.data.mapper

import io.fajarca.project.jetnews.data.db.entity.SearchHistoryEntity
import io.fajarca.project.jetnews.domain.entity.SearchHistory
import javax.inject.Inject

class SearchHistoryEntityMapper @Inject constructor() {

    fun toEntity(input: SearchHistory): SearchHistoryEntity {
        return SearchHistoryEntity(input.query, input.createdAt)
    }

    fun fromEntity(input: List<SearchHistoryEntity>): List<SearchHistory> {
        return input.map { SearchHistory(it.query, it.createdAt) }
    }

}