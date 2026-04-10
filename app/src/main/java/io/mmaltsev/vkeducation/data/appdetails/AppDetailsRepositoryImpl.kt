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

    override suspend fun getAppDetails(id: String): AppDetails {
        return withContext(Dispatchers.IO) {
            val entity = dao.getAppDetails(id)

            if (entity != null) {
                return@withContext entityMapper.toDomain(entity)
            } else {
                val dto = appApi.getAppDetails(id)
                val domain = appDetailsMapper.toDomain(dto)
                val newEntity = entityMapper.toEntity(domain)
                dao.insertAppDetails(newEntity)
                return@withContext domain
            }
        }
    }

    override fun observeAppDetails(id: String): Flow<AppDetails> {
        return flow {
            val details = getAppDetails(id)
            emit(details)
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