package io.fajarca.project.jetnews.data.service

import io.fajarca.project.jetnews.data.response.TopHeadlinesDto
import retrofit2.http.GET

interface NewsService {
    @GET("top-headlines?country=id&page=1&pageSize=25")
    suspend fun getTopHeadlines() : TopHeadlinesDto
}