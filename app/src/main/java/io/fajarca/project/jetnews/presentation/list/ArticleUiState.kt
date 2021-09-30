package io.fajarca.project.jetnews.presentation.list

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ArticleUiState(
    val articles : Flow<PagingData<ArticleUiModel>> = flowOf(),
    val isLoading: Boolean,
    val newsSource : List<String> = emptyList()
)
