package io.fajarca.project.jetnews.domain.repository

import androidx.paging.PagingData
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(): Flow<PagingData<TopHeadline>>
    suspend fun getNewsSource(): Flow<List<String>>
    suspend fun toggleBookmark(title: String): Int
}