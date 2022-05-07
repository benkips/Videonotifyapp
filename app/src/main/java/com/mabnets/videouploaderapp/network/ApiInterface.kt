package com.mabnets.videouploaderapp.network

import com.mabnets.videouploaderapp.models.Details
import com.mabnets.videouploaderapp.models.Heal
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    companion object{
        const val BASE_URL="https://mobile.repentanceandholinessinfo.com/"
    }

    //getting healings
    @POST("mobiadmin/fullhealings")
    @FormUrlEncoded
    suspend fun  gethealings(@Field("pg") x: Int?, @Field("count")q: Int?): Heal
    //delete healings
    @POST("mobiadmin/deletez")
    @FormUrlEncoded
    suspend  fun deletehealing(@Field("id") id: Int?,@Field("imgfile")q: String?): Details

}