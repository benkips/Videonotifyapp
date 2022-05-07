package com.mabnets.videouploaderapp.Repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mabnets.videouploaderapp.network.ApiInterface
import com.mabnets.videouploaderapp.paging.HealingPagSource

import javax.inject.Inject
import javax.inject.Singleton


class Repostuff @Inject constructor (private val apiInterface: ApiInterface):Baserepository(){

    fun gethealingz() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HealingPagSource(apiInterface) }
        ).liveData

    //retrivelocations
    suspend fun deletehealings(id: Int,img:String?) = safeApiCall {
        apiInterface.deletehealing(id,img)
    }
}