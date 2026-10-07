package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.data.model.VolumeDto
import com.sadturtleman.kuit.data.model.VolumeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {

    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("maxResults") max: Int = 20
    ): VolumeResponse

    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id: String,
        @Query("key") apiKey: String
    ): VolumeDto
}