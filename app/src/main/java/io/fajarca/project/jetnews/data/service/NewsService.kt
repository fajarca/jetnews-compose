package io.fajarca.project.jetnews.data.service

import io.fajarca.project.jetnews.data.response.ArticlesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "id",
        @Query("page") page: Int,
        @Query("pageSize") size: Int
    ): ArticlesDto

    @GET("everything")
    suspend fun search(
        @Query("q") query: String,
        @Query("language") language: String = "en",
        @Query("page") page: Int,
        @Query("pageSize") size: Int
    ): ArticlesDto
}