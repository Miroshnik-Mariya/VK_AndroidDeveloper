package io.mmaltsev.vkeducation.data.appdetails

import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import io.mmaltsev.vkeducation.domain.appdetails.AppDetailsRepository
import javax.inject.Inject

class AppDetailsRepositoryImpl @Inject constructor(
    private val appApi: AppApi,
    private val mapper: AppDetailsMapper
) : AppDetailsRepository {

    override suspend fun getAppDetails(id: String): AppDetails {
        return try {
            android.util.Log.d("AppDetailsRepo", "Загрузка деталей для id: $id")
            val dto = appApi.getAppDetails(id)
            android.util.Log.d("AppDetailsRepo", "Получены детали: ${dto.name}")
            mapper.toDomain(dto)
        } catch (e: Exception) {
            android.util.Log.e("AppDetailsRepo", "Ошибка", e)
            throw Exception("Ошибка загрузки деталей: ${e.message}")
        }
    }
}