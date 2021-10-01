package io.fajarca.project.jetnews.domain.entity

import java.util.*

data class SearchHistory(
    val query: String,
    val createdAt: Date
)