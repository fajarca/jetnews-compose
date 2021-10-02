package io.fajarca.project.jetnews.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fajarca.project.jetnews.domain.usecase.article.GetTopHeadlinesUseCase
import io.fajarca.project.jetnews.domain.usecase.article.ToggleBookmarkUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ArticleViewState(loading = true))
    val state: StateFlow<ArticleViewState> = _state

    init {
        processEvent(MainViewEvent.FetchTopHeadlines)
    }

    fun processEvent(event : MainViewEvent) {
        when (event) {
            is MainViewEvent.BookmarkArticle -> {
                viewModelScope.launch {
                    toggleBookmarkUseCase.execute(event.title)
                }
            }
            MainViewEvent.FetchTopHeadlines -> {
                val articles = getTopHeadlinesUseCase.execute()
                _state.update { uiState ->
                    uiState.copy(
                        loading = false,
                        articles = articles
                    )
                }

            }
        }
    }
}