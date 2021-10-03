package io.fajarca.project.jetnews.presentation.list

import androidx.paging.PagingData
import io.fajarca.project.jetnews.infrastructure.abstraction.ViewEffect
import io.fajarca.project.jetnews.infrastructure.abstraction.ViewEvent
import io.fajarca.project.jetnews.infrastructure.abstraction.ViewState
import kotlinx.coroutines.flow.Flow

class MainContract {

    sealed class Event : ViewEvent {
        object FetchTopHeadlines : Event()
        object ViewBookmarkedArticle : Event()
        object SearchArticle : Event()
        object PullRefresh : Event()
        data class BookmarkArticle(val article : ArticleUiModel) : Event()
        data class ArticleSelection(val article : ArticleUiModel) : Event()
    }

    data class State(val loading : Boolean, val articles : Flow<PagingData<ArticleUiModel>>) : ViewState

    sealed class Effect : ViewEffect {

        object ShowToast : Effect()
        object PullRefresh : Effect()

        sealed class Navigation : Effect() {
            data class ToArticleDetail(val article: ArticleUiModel) : Navigation()
            object ToSearchArticleScreen : Navigation()
            object ToBookmarkScreen : Navigation()
        }
    }
}