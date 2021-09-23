package io.fajarca.project.jetnews.domain.repository

import io.fajarca.project.jetnews.domain.entity.TopHeadline
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines() : Flow<List<TopHeadline>>
    suspend fun getNewsSource() : Flow<List<String>>
    suspend fun toggleBookmark(title : String) : Int
}