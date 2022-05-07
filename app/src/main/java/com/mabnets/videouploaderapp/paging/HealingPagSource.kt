package com.mabnets.videouploaderapp.paging

import androidx.paging.PagingSource
import com.mabnets.videouploaderapp.models.Healings
import com.mabnets.videouploaderapp.network.ApiInterface
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
class HealingPagSource(private val apiInterface: ApiInterface,
):PagingSource<Int, Healings>() {
    override suspend fun load(params:LoadParams<Int>):LoadResult<Int, Healings> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apiInterface.gethealings(position, params.loadSize)
            val news = response.data
            LoadResult.Page(
                data = news,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (news.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}