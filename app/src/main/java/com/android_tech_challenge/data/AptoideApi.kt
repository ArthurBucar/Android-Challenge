package com.android_tech_challenge.data

import retrofit2.http.GET

interface AptoideApi {
    @GET("api/6/bulkRequest/api_list/listApps?store_name=apps&limit=10")
    suspend fun getApps(): MainResponse
} 