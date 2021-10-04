package io.fajarca.project.jetnews.presentation.list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fajarca.project.jetnews.domain.usecase.article.GetBookmarkedArticlesCountUseCase
import io.fajarca.project.jetnews.domain.usecase.article.GetTopHeadlinesUseCase
import io.fajarca.project.jetnews.domain.usecase.article.ToggleBookmarkUseCase
import io.fajarca.project.jetnews.infrastructure.abstraction.BaseViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val getBookmarkedArticlesCountUseCase: GetBookmarkedArticlesCountUseCase
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    init {
        getTopHeadlines()
        getBookmarkedArticlesCount()
    }

    override fun createInitialState(): MainContract.State {
        return MainContract.State(true, flowOf(), -1)
    }

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.BookmarkArticle -> toggleArticleBookmark(event.article.title)
            MainContract.Event.FetchTopHeadlines -> getTopHeadlines()
            is MainContract.Event.ArticleSelection -> {
                setEffect { MainContract.Effect.Navigation.ToArticleDetail(event.article) }
            }
            MainContract.Event.PullRefresh -> {
                setEffect { MainContract.Effect.ShowRefreshSuccessSnackBar }
                setEffect { MainContract.Effect.PullRefresh }
            }
            MainContract.Event.SearchArticle -> setEffect { MainContract.Effect.Navigation.ToSearchArticleScreen }
            MainContract.Event.ViewBookmarkedArticle -> setEffect { MainContract.Effect.Navigation.ToBookmarkScreen }
        }
    }

    private fun toggleArticleBookmark(title: String) {
        viewModelScope.launch {
            toggleBookmarkUseCase.execute(title)
        }
    }

    private fun getTopHeadlines() {
        val articles = getTopHeadlinesUseCase.execute()
        setState { copy(loading = false, articles = articles) }
    }

    private fun getBookmarkedArticlesCount() {
        viewModelScope.launch {
            getBookmarkedArticlesCountUseCase.execute().collect {
                setState { copy(bookmarkCount = it) }
            }
        }

    }
}