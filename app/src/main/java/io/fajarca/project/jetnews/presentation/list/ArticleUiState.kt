package io.fajarca.project.jetnews.presentation.list

import androidx.paging.PagingData
import io.fajarca.project.jetnews.domain.entity.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ArticleUiState(
    val articles : Flow<PagingData<Article>> = flowOf(),
    val isLoading: Boolean,
    val newsSource : List<String> = emptyList()
)
