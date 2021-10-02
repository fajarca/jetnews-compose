package io.fajarca.project.jetnews.presentation.list

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ArticleViewState(
    val articles : Flow<PagingData<ArticleUiModel>> = flowOf(),
    val loading: Boolean
)
