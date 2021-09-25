package io.fajarca.project.jetnews.presentation.list

import androidx.paging.PagingData
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MovieUiState(
    val headlines : Flow<PagingData<TopHeadline>> = flowOf(),
    val isLoading: Boolean,
    val newsSource : List<String> = emptyList()
)
