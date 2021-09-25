package io.fajarca.project.jetnews.presentation.list

data class MovieUiState(
    val isLoading: Boolean,
    val newsSource : List<String> = emptyList()
)
