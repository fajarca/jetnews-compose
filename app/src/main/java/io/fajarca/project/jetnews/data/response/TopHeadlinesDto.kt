package io.fajarca.project.jetnews.data.response

import com.squareup.moshi.Json

data class TopHeadlinesDto(
    @field:Json(name = "articles")
    val articles: List<Article> = emptyList()
) {
    data class Article(
        @field:Json(name = "author")
        val author: String? = null,
        @field:Json(name = "content")
        val content: String? = null,
        @field:Json(name = "description")
        val description: String? = null,
        @field:Json(name = "publishedAt")
        val publishedAt: String? = null,
        @field:Json(name = "source")
        val source: Source? = null,
        @field:Json(name = "title")
        val title: String? = null,
        @field:Json(name = "url")
        val url: String? = null,
        @field:Json(name = "urlToImage")
        val urlToImage: String? = null
    ) {
        data class Source(
            @field:Json(name = "id")
            val id: Any? = null,
            @field:Json(name = "name")
            val name: String? = null
        )
    }
}