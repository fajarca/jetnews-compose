package io.fajarca.project.jetnews.data.source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import io.fajarca.project.jetnews.data.db.AppDatabase
import io.fajarca.project.jetnews.data.db.TopHeadlineEntity
import io.fajarca.project.jetnews.data.mapper.TopHeadlinesEntityMapper
import io.fajarca.project.jetnews.util.extension.getOrNull
import javax.inject.Inject
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val entityMapper: TopHeadlinesEntityMapper,
    private val database: AppDatabase,
    private val remoteDataSource: NewsRemoteDataSource
) : RemoteMediator<Int, TopHeadlineEntity>() {

    private var currentPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopHeadlineEntity>
    ): MediatorResult {

        return try {
            when (loadType) {
                LoadType.REFRESH -> {
                    null
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    currentPage++
                }
            }


            Log.d("Paging", "Load key is $currentPage")

            val response =
                remoteDataSource.getTopHeadlines("id", currentPage, state.config.pageSize).getOrNull()

            database.withTransaction {
                val headlines = entityMapper.toEntity(response ?: return@withTransaction)
                database.topHeadlineDao().insertAll(*headlines.toTypedArray())
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