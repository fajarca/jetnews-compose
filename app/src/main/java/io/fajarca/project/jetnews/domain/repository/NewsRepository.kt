package io.fajarca.project.jetnews.domain.repository

import androidx.paging.PagingData
import io.fajarca.project.jetnews.domain.entity.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(): Flow<PagingData<Article>>
    suspend fun getNewsSource(): Flow<List<String>>
    suspend fun toggleBookmark(title: String): Int
    fun searchNews(query: String, language: String): Flow<PagingData<Article>>
}