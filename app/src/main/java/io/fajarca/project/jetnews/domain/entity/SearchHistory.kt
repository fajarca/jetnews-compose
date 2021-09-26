package io.fajarca.project.jetnews.domain.entity

import java.util.*

data class SearchHistory(
    val id: Int = 0,
    val query: String,
    val createdAt: Date
)