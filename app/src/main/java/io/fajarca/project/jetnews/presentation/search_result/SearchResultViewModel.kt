package io.fajarca.project.jetnews.presentation.search_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fajarca.project.jetnews.domain.entity.SearchHistory
import io.fajarca.project.jetnews.domain.usecase.ClearSearchHistoryUseCase
import io.fajarca.project.jetnews.domain.usecase.GetSearchHistoryUseCase
import io.fajarca.project.jetnews.domain.usecase.InsertSearchHistoryUseCase
import io.fajarca.project.jetnews.domain.usecase.SearchNewsUseCase
import io.fajarca.project.jetnews.domain.usecase.ToggleBookmarkUseCase
import io.fajarca.project.jetnews.presentation.list.ArticleUiModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchResultUiModel(
    val searchResult: Flow<PagingData<ArticleUiModel>> = flowOf(),
)

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchResultUiModel())
    val uiState: StateFlow<SearchResultUiModel> = _uiState


    fun searchNews(query: String, language: String) {
        _uiState.update { uiState ->
            val result = searchNewsUseCase.execute(query, language)
            uiState.copy(searchResult = result)
        }

    }

    fun toggleBookmark(title: String) {
        viewModelScope.launch {
            toggleBookmarkUseCase.execute(title)
        }
    }

}