package io.fajarca.project.jetnews.presentation.list

import io.fajarca.project.jetnews.util.date.TimeDifference

class ArticleUiModel(
    val description: String,
    val title: String,
    val url: String,
    val imageUrl: String,
    val source: String,
    val isBookmarked: Boolean,
    val timeDifference: TimeDifference
)
