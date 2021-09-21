package io.fajarca.project.jetnews

interface NewsRepository {
    suspend fun getNews() : List<News>
}