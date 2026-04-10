package io.mmaltsev.vkeducation.data.appdetails

import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsDao
import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsEntityMapper
import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import io.mmaltsev.vkeducation.domain.appdetails.AppDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppDetailsRepositoryImpl @Inject constructor(
    private val appApi: AppApi,
    private val dao: AppDetailsDao,
    private val appDetailsMapper: AppDetailsMapper,
    private val entityMapper: AppDetailsEntityMapper
) : AppDetailsRepository {

    // Одноразовый запрос (для первоначальной загрузки)
    override suspend fun getAppDetails(id: String): AppDetails {
        return withContext(Dispatchers.IO) {
            // 1. Сначала проверяем БД
            val entity = dao.getAppDetails(id)

            if (entity != null) {
                // 2. Если есть в БД - возвращаем из кэша
                return@withContext entityMapper.toDomain(entity)
            } else {
                // 3. Если нет в БД - идём в сеть
                val dto = appApi.getAppDetails(id)
                val domain = appDetailsMapper.toDomain(dto)
                val newEntity = entityMapper.toEntity(domain)

                // 4. Сохраняем в БД на IO потоке
                dao.insertAppDetails(newEntity)

                // 5. Возвращаем результат
                return@withContext domain
            }
        }
    }

    // Наблюдение за изменениями (для wishlist)
    override fun observeAppDetails(id: String): Flow<AppDetails> {
        // Наблюдаем за изменениями в БД (например, когда обновляется wishlist)
        return dao.observeAppDetails(id)
            .map { entity ->
                if (entity != null) {
                    entityMapper.toDomain(entity)
                } else {
                    // Если нет в БД, загружаем из сети
                    val dto = appApi.getAppDetails(id)
                    val domain = appDetailsMapper.toDomain(dto)
                    val newEntity = entityMapper.toEntity(domain)
                    dao.insertAppDetails(newEntity)
                    domain
                }
            }
    }

    override suspend fun toggleWishlist(id: String) {
        withContext(Dispatchers.IO) {
            val currentEntity = dao.getAppDetails(id)
            if (currentEntity != null) {
                val newStatus = !currentEntity.isInWishlist
                dao.updateWishlistStatus(id, newStatus)
            }
        }
    }
}