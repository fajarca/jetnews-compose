package io.fajarca.project.jetnews.domain.entity

data class TopHeadline(
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val imageUrl: String
)