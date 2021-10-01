package io.fajarca.project.jetnews.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fajarca.project.jetnews.domain.usecase.article.GetBookmarkedArticlesUseCase
import io.fajarca.project.jetnews.domain.usecase.article.ToggleBookmarkUseCase
import io.fajarca.project.jetnews.presentation.list.ArticleUiModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BookmarkUiModel(
    val articles: List<ArticleUiModel> = emptyList()
)

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkedArticlesUseCase: GetBookmarkedArticlesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookmarkUiModel())
    val uiState: StateFlow<BookmarkUiModel> = _uiState

    init {
        getBookmarkedArticles()
    }

    private fun getBookmarkedArticles() {
        viewModelScope.launch {
            getBookmarkedArticlesUseCase.execute().collect {
                _uiState.update { uiState -> uiState.copy(articles = it) }
            }
        }
    }

    fun toggleBookmark(title: String) {
        viewModelScope.launch {
            toggleBookmarkUseCase.execute(title)
        }
    }
}