package io.fajarca.project.jetnews.data.source

import io.fajarca.project.jetnews.infrastructure.apiclient.ApiClient
import io.fajarca.project.jetnews.data.service.NewsService
import io.fajarca.project.jetnews.data.response.TopHeadlinesDto
import io.fajarca.project.jetnews.domain.Either
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val apiClient: ApiClient,
    private val newsService: NewsService
) {

    suspend fun getTopHeadlines(country : String, page : Int, size : Int): Either<Exception, TopHeadlinesDto> {
        return apiClient.call { newsService.getTopHeadlines(country, page, size) }
    }


}