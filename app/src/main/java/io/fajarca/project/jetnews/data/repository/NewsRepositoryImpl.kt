package io.fajarca.project.jetnews.data.repository

import io.fajarca.project.jetnews.domain.repository.NewsRepository
import io.fajarca.project.jetnews.data.mapper.TopHeadlinesEntityMapper
import io.fajarca.project.jetnews.data.mapper.TopHeadlinesMapper
import io.fajarca.project.jetnews.data.source.NewsRemoteDataSource
import io.fajarca.project.jetnews.domain.Either
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val mapper: TopHeadlinesMapper,
    private val entityMapper: TopHeadlinesEntityMapper,
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {


    override suspend fun getTopHeadlines(): Either<Exception, List<TopHeadline>> {
        val apiResult = remoteDataSource.getTopHeadlines()
        return when (apiResult) {
            is Either.Left -> Either.Left(apiResult.cause)
            is Either.Right -> {
                val news = mapper.map(apiResult.data)
                Either.Right(news)
            }
        }
    }

}