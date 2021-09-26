package io.fajarca.project.jetnews.data.mapper

import io.fajarca.project.jetnews.data.response.ArticlesDto
import io.fajarca.project.jetnews.domain.entity.Article
import javax.inject.Inject

class ArticlesMapper @Inject constructor() {

    fun map(input: ArticlesDto.Article): Article {
        return Article(
            title = input.title.orEmpty(),
            description = input.description.orEmpty(),
            url = input.url.orEmpty(),
            imageUrl = input.urlToImage.orEmpty(),
            publishedAt = input.publishedAt.orEmpty(),
            source = input.source?.name.orEmpty(),
            isBookmarked = false
        )
    }

}