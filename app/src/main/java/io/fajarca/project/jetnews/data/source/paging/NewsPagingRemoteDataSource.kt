package io.fajarca.project.jetnews.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.fajarca.project.jetnews.data.response.TopHeadlinesDto
import io.fajarca.project.jetnews.domain.Either
import io.fajarca.project.jetnews.util.extension.getOrNull
import javax.inject.Inject

class NewsPagingRemoteDataSource @Inject constructor(
    private val apiCall : suspend (Int, Int) -> Either<Exception, TopHeadlinesDto>
) : PagingSource<Int, TopHeadlinesDto.Article>() {


    override fun getRefreshKey(state: PagingState<Int, TopHeadlinesDto.Article>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopHeadlinesDto.Article> {
        return try {
            var nextPage = params.key ?: 1

            val response = apiCall(nextPage, params.loadSize)

            val articles = response.getOrNull()?.articles ?: emptyList()

            if (response is Either.Right) {
                nextPage++
            }

            LoadResult.Page(articles, null, nextPage)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}