package io.fajarca.project.jetnews.presentation.list

sealed class MainViewEvent {
    object FetchTopHeadlines : MainViewEvent()
    data class BookmarkArticle(val title : String) : MainViewEvent()
}