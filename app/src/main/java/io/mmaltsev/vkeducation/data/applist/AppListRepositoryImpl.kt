package io.mmaltsev.vkeducation.data.applist

import io.mmaltsev.vkeducation.domain.model.App
import io.mmaltsev.vkeducation.domain.repository.AppListRepository
import javax.inject.Inject

class AppListRepositoryImpl @Inject constructor(
    private val api: AppListApi,
    private val mapper: AppListMapper
) : AppListRepository {

    override suspend fun getApps(): List<App> {
        return try {
            android.util.Log.d("AppListRepo", "Начинаем загрузку...")
            val response = api.getCatalog()

            // Логируем количество и первый элемент
            android.util.Log.d("AppListRepo", "Загружено ${response.size} приложений")

            if (response.isNotEmpty()) {
                val first = response.first()
                android.util.Log.d("AppListRepo", "Пример данных: id=${first.id}, name=${first.name}, category=${first.category}")
            }

            mapper.toDomainList(response)
        } catch (e: Exception) {
            android.util.Log.e("AppListRepo", "Ошибка загрузки", e)
            throw Exception("Ошибка загрузки каталога: ${e.message}")
        }
    }

}