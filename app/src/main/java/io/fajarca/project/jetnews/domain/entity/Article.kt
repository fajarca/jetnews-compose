package io.fajarca.project.jetnews.domain.entity

data class Article(
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val imageUrl: String,
    val source: String,
    val isBookmarked: Boolean
)