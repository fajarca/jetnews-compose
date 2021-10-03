package io.fajarca.project.jetnews.presentation.list

import androidx.paging.PagingData
import io.fajarca.project.jetnews.infrastructure.abstraction.UiEffect
import io.fajarca.project.jetnews.infrastructure.abstraction.UiEvent
import io.fajarca.project.jetnews.infrastructure.abstraction.UiState
import kotlinx.coroutines.flow.Flow

class MainContract {

    sealed class Event : UiEvent {
        object FetchTopHeadlines : Event()
        data class BookmarkArticle(val title : String) : Event()
    }

    data class State(val loading : Boolean, val articles : Flow<PagingData<ArticleUiModel>>) : UiState

    // Side effects
    sealed class Effect : UiEffect {

        object ShowToast : Effect()

    }
}