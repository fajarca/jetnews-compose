package io.fajarca.project.jetnews.data.mapper

import io.fajarca.project.jetnews.data.db.entity.ArticleEntity
import io.fajarca.project.jetnews.data.response.ArticlesDto
import io.fajarca.project.jetnews.domain.entity.Article
import javax.inject.Inject

class ArticlesEntityMapper @Inject constructor() {

    fun toEntity(input: ArticlesDto): List<ArticleEntity> {
        return input.articles.map {
            ArticleEntity(
                title = it.title.orEmpty(),
                description = it.description.orEmpty(),
                url = it.url.orEmpty(),
                imageUrl = it.urlToImage.orEmpty(),
                publishedAt = it.publishedAt.orEmpty(),
                source = it.source?.name.orEmpty(),
                isBookmarked = false
            )
        }
    }

    fun fromEntities(input: List<ArticleEntity>): List<Article> {
        return input.map {
            Article(
                title = it.title,
                description = it.description,
                url = it.url,
                imageUrl = it.imageUrl,
                publishedAt = it.publishedAt,
                source = it.source,
                isBookmarked = it.isBookmarked
            )
        }
    }


    fun fromEntity(input: ArticleEntity): Article {
        return Article(
            title = input.title,
            description = input.description,
            url = input.url,
            imageUrl = input.imageUrl,
            publishedAt = input.publishedAt,
            source = input.source,
            isBookmarked = input.isBookmarked
        )
    }

}