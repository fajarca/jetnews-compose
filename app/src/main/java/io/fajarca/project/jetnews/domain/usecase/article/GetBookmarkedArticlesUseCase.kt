package io.fajarca.project.jetnews.domain.usecase.article

import io.fajarca.project.jetnews.domain.entity.Article
import io.fajarca.project.jetnews.domain.repository.NewsRepository
import io.fajarca.project.jetnews.presentation.list.ArticleUiModel
import io.fajarca.project.jetnews.util.constant.DateTimeFormat
import io.fajarca.project.jetnews.util.date.DateManager
import io.fajarca.project.jetnews.util.extension.toDate
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBookmarkedArticlesUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val dateManager: DateManager
) {

    suspend fun execute(): Flow<List<ArticleUiModel>> {
        return repository.getBookmarkedNews()
            .map { articles -> formatPublishedAt(articles) }
    }

    private fun formatPublishedAt(articles: List<Article>): List<ArticleUiModel> {
        return articles.map { article ->
            val timeDifference =
                dateManager.getTimeDifference(
                    Date(),
                    article.publishedAt.toDate(DateTimeFormat.FULL)
                )

            ArticleUiModel(
                article.description,
                article.title,
                article.url,
                article.imageUrl,
                article.source,
                article.isBookmarked,
                timeDifference
            )
        }
    }
}
