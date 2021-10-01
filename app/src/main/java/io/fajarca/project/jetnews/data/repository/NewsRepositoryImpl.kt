package io.fajarca.project.jetnews.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import io.fajarca.project.jetnews.data.mapper.ArticlesEntityMapper
import io.fajarca.project.jetnews.data.mapper.ArticlesMapper
import io.fajarca.project.jetnews.data.source.NewsLocalDataSource
import io.fajarca.project.jetnews.data.source.NewsRemoteDataSource
import io.fajarca.project.jetnews.data.source.paging.NewsPagingRemoteDataSource
import io.fajarca.project.jetnews.data.source.paging.NewsPagingRemoteMediator
import io.fajarca.project.jetnews.domain.entity.Article
import io.fajarca.project.jetnews.domain.repository.NewsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl @Inject constructor(
    private val entityMapper: ArticlesEntityMapper,
    private val mapper: ArticlesMapper,
    private val localDataSource: NewsLocalDataSource,
    private val remoteDataSource: NewsRemoteDataSource,
    private val pagingRemoteMediator: NewsPagingRemoteMediator,
) : NewsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopHeadlines(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 5, initialLoadSize = 3 * 5),
            remoteMediator = pagingRemoteMediator
        ) { localDataSource.findAll() }
            .flow
            .map { pagingData ->
                pagingData.map { headline ->
                    entityMapper.fromEntity(headline)
                }
            }
    }

    override suspend fun getNewsSource(): Flow<List<String>> {
        return localDataSource.findAllNewsSource()
    }

    override suspend fun toggleBookmark(title: String): Int {
        return localDataSource.toggleBookmark(title)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun searchNews(query: String, language: String): Flow<PagingData<Article>> {
        return Pager(PagingConfig(pageSize = 10, initialLoadSize = 2 * 10)) {
            NewsPagingRemoteDataSource { page, pageSize ->
                remoteDataSource.search(query, language, page, pageSize)
            }
        }
            .flow
            .map { pagingData ->
                pagingData.map { article ->
                    mapper.map(article)
                }
            }
    }

    override suspend fun getBookmarkedNews(): Flow<List<Article>> {
        return localDataSource.findAllBookmarked().map { entityMapper.fromEntities(it) }
    }
}