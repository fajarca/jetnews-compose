package io.fajarca.project.jetnews.domain.usecase.article

import androidx.paging.PagingData
import androidx.paging.map
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

class GetTopHeadlinesUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val dateManager: DateManager
) {

    fun execute(): Flow<PagingData<ArticleUiModel>> {
        return repository.getTopHeadlines()
            .map { pagingData ->
                pagingData.map { headline ->
                    formatPublishedAt(headline)
                }
            }
    }

    private fun formatPublishedAt(article: Article): ArticleUiModel {

        val timeDifference =
            dateManager.getTimeDifference(Date(), article.publishedAt.toDate(DateTimeFormat.FULL))

        return ArticleUiModel(
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
