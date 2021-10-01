package io.fajarca.project.jetnews.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fajarca.project.jetnews.domain.entity.SearchHistory
import io.fajarca.project.jetnews.domain.usecase.search_history.ClearSearchHistoryUseCase
import io.fajarca.project.jetnews.domain.usecase.search_history.GetSearchHistoryUseCase
import io.fajarca.project.jetnews.domain.usecase.search_history.InsertSearchHistoryUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUiModel(
    val text: String = "",
    val searchHistories: List<SearchHistory> = emptyList()
)

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiModel())
    val uiState: StateFlow<SearchUiModel> = _uiState

    init {
        getSearchHistory()
    }

    fun recordSearchHistory(query: String) {
        viewModelScope.launch {
            insertSearchHistoryUseCase.execute(query)
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update { uiState ->
            uiState.copy(text = query)
        }
    }

    private fun getSearchHistory() {
        viewModelScope.launch {
            getSearchHistoryUseCase.execute().collectLatest { searchHistories ->
                _uiState.update { uiState -> uiState.copy(searchHistories = searchHistories) }
            }
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            clearSearchHistoryUseCase.execute()
        }
    }

}