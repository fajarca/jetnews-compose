package io.fajarca.project.jetnews.data.repository

import io.fajarca.project.jetnews.domain.repository.NewsRepository
import io.fajarca.project.jetnews.data.mapper.TopHeadlinesEntityMapper
import io.fajarca.project.jetnews.data.source.NewsLocalDataSource
import io.fajarca.project.jetnews.data.source.NewsRemoteDataSource
import io.fajarca.project.jetnews.domain.Either
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl @Inject constructor(
    private val entityMapper: TopHeadlinesEntityMapper,
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource
) : NewsRepository {

    override suspend fun getTopHeadlines(): Flow<List<TopHeadline>> {
        val apiResult = remoteDataSource.getTopHeadlines()

        return when (apiResult) {
            is Either.Left -> localDataSource.findAll().map { entityMapper.fromEntity(it) }
            is Either.Right -> {
                localDataSource.insertAll(entityMapper.toEntity(apiResult.data))
                localDataSource.findAll().map { entityMapper.fromEntity(it) }
            }
        }
    }

    override suspend fun getNewsSource(): Flow<List<String>> {
        return localDataSource.findAllNewsSource()
    }

}