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
import io.fajarca.project.jetnews.data.service.NewsService
import javax.inject.Inject
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val entityMapper: TopHeadlinesEntityMapper,
    private val database: AppDatabase,
    private val newsService: NewsService
) : RemoteMediator<Int, TopHeadlineEntity>() {

    private val dao = database.topHeadlineDao()

    private var currentPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopHeadlineEntity>
    ): MediatorResult {

        return try {
            when(loadType) {
                LoadType.REFRESH -> {
                    Log.d("Paging", "State refresh")
                    null
                }
                LoadType.PREPEND ->    {
                    Log.d("Paging", "State prepend")
                   return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    Log.d("Paging", "State append")


                    currentPage++
                    Log.d("Paging", "State append. Current page is $currentPage")
                }
            }


            Log.d("Paging", "Load key is $currentPage")

            val response = newsService.getTopHeadlines("id", currentPage ?: 1 , state.config.pageSize)
            database.withTransaction {
                val headlines = entityMapper.toEntity(response)
                dao.insertAll(*headlines.toTypedArray())
            }

            MediatorResult.Success(
                endOfPaginationReached = response.articles.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

}