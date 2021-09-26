package io.fajarca.project.jetnews.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fajarca.project.jetnews.domain.entity.Article
import io.fajarca.project.jetnews.domain.usecase.InsertSearchHistoryUseCase
import io.fajarca.project.jetnews.domain.usecase.SearchNewsUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUiModel(
    val isLoading: Boolean = false,
    val text: String = "",
    val searchResult: Flow<PagingData<Article>> = flowOf()
)

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiModel(isLoading = true))
    val uiState: StateFlow<SearchUiModel> = _uiState

    fun searchNews(query: String, language: String) {
        viewModelScope.launch {
            insertSearchHistoryUseCase.execute(query)
        }

        _uiState.update { uiState ->
            val result = searchNewsUseCase.execute(query, language)
            uiState.copy(isLoading = false, searchResult = result)
        }

    }

    fun onQueryChange(query: String) {
        _uiState.update { uiState ->
            uiState.copy(isLoading = false, text = query)
        }
    }

}