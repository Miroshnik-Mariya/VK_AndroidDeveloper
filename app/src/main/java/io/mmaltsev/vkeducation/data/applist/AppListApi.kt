package io.mmaltsev.vkeducation.data.applist

import retrofit2.http.GET
import retrofit2.http.Path

interface AppListApi {
    @GET("catalog")
    suspend fun getCatalog(): List<AppListDto>
}