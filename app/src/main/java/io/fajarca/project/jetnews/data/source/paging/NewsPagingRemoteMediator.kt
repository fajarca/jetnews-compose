package io.fajarca.project.jetnews.data.source.paging

import android.database.sqlite.SQLiteConstraintException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import io.fajarca.project.jetnews.data.db.AppDatabase
import io.fajarca.project.jetnews.data.db.entity.ArticleEntity
import io.fajarca.project.jetnews.data.mapper.ArticlesEntityMapper
import io.fajarca.project.jetnews.data.source.NewsRemoteDataSource
import io.fajarca.project.jetnews.util.extension.getOrNull
import javax.inject.Inject
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class NewsPagingRemoteMediator @Inject constructor(
    private val entityMapper: ArticlesEntityMapper,
    private val database: AppDatabase,
    private val remoteDataSource: NewsRemoteDataSource
) : RemoteMediator<Int, ArticleEntity>() {

    private var currentPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {

        return try {
            when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    currentPage++
                }
            }

            val response =
                remoteDataSource.getTopHeadlines("id", currentPage, state.config.pageSize).getOrNull()

            database.withTransaction {
                val headlines = entityMapper.toEntity(response ?: return@withTransaction)
                try {
                    database.articleDao().insertAll(*headlines.toTypedArray())
                } catch (e: SQLiteConstraintException) {}
            }

            MediatorResult.Success(
                endOfPaginationReached = response?.articles?.isEmpty() ?: false
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

}