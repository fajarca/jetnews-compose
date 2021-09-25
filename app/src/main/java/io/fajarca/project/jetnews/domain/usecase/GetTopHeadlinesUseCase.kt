package io.fajarca.project.jetnews.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import io.fajarca.project.jetnews.domain.repository.NewsRepository
import io.fajarca.project.jetnews.util.constant.DateTimeFormat
import io.fajarca.project.jetnews.util.extension.getDifferenceInHours
import io.fajarca.project.jetnews.util.extension.toDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTopHeadlinesUseCase @Inject constructor(private val repository: NewsRepository) {

    fun execute(): Flow<PagingData<TopHeadline>> {
        return repository.getTopHeadlines()
            .map { pagingData ->
                pagingData.map { headline ->
                    formatPublishedAt(headline)
                }
            }
    }

    private fun formatPublishedAt(headline: TopHeadline): TopHeadline {
        return headline.copy(
            publishedAt = "${
                headline.publishedAt.toDate(DateTimeFormat.FULL).getDifferenceInHours()
            } jam yang lalu"
        )
    }
}
