package io.fajarca.project.jetnews.presentation.list

import io.fajarca.project.jetnews.domain.entity.TopHeadline

data class MovieUiState(
    val topHeadlines: List<TopHeadline> = emptyList(),
    val isLoading: Boolean,
    val newsSource : List<String> = emptyList()
)
