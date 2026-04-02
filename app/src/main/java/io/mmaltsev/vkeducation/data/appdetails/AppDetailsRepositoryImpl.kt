package io.mmaltsev.vkeducation.data.appdetails

import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsDao
import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsEntityMapper
import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import io.mmaltsev.vkeducation.domain.appdetails.AppDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppDetailsRepositoryImpl @Inject constructor(
    private val appApi: AppApi,
    private val dao: AppDetailsDao,
    private val mapper: AppDetailsMapper,
    private val entityMapper: AppDetailsEntityMapper
) : AppDetailsRepository {

    // Одноразовый запрос (для первоначальной загрузки)
    override suspend fun getAppDetails(id: String): AppDetails {
        // Сначала проверяем кэш
        val cached = dao.getAppDetails(id)
        if (cached != null) {
            return entityMapper.toDomain(cached)
        }

        // Если нет в кэше, загружаем из сети
        val dto = appApi.getAppDetails(id)
        val domain = mapper.toDomain(dto)
        val entity = entityMapper.toEntity(domain)
        dao.insertAppDetails(entity)
        return domain
    }

    // Подписка на изменения (для реального времени)
    override fun observeAppDetails(id: String): Flow<AppDetails> {
        return dao.observeAppDetails(id)
            .map { entity ->
                if (entity != null) {
                    entityMapper.toDomain(entity)
                } else {
                    // Если нет в БД, загружаем синхронно и возвращаем
                    val dto = appApi.getAppDetails(id)
                    val domain = mapper.toDomain(dto)
                    val newEntity = entityMapper.toEntity(domain)
                    dao.insertAppDetails(newEntity)
                    domain
                }
            }
            .catch { e ->
                android.util.Log.e("AppDetailsRepo", "Error observing", e)
                throw e
            }
    }

    // Переключение статуса wishlist
    override suspend fun toggleWishlist(id: String) {
        val currentEntity = dao.getAppDetails(id)  // ← это suspend fun, возвращает AppDetailsEntity?
        if (currentEntity != null) {
            val newStatus = !currentEntity.isInWishlist
            dao.updateWishlistStatus(id, newStatus)
            android.util.Log.d("AppDetailsRepo", "Wishlist for $id toggled to $newStatus")
        }
    }
}