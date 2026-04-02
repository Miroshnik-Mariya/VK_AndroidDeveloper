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
    private val mapper: AppDetailsMapper,
    private val entityMapper: AppDetailsEntityMapper
) : AppDetailsRepository {

    override suspend fun getAppDetails(id: String): AppDetails {
        return withContext(Dispatchers.IO) {
            val cached = dao.getAppDetails(id)
            if (cached != null) {
                return@withContext entityMapper.toDomain(cached)
            }

            val dto = appApi.getAppDetails(id)
            val domain = mapper.toDomain(dto)
            val entity = entityMapper.toEntity(domain)
            dao.insertAppDetails(entity)
            return@withContext domain
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
                android.util.Log.d("AppDetailsRepo", "Wishlist for $id toggled to $newStatus")
            }
        }
    }
}