package io.mmaltsev.vkeducation.domain.appdetails

import kotlinx.coroutines.flow.Flow

interface AppDetailsRepository {
    suspend fun getAppDetails(id: String): AppDetails
    fun observeAppDetails(id: String): Flow<AppDetails>  // ← подписка на изменения
    suspend fun toggleWishlist(id: String) // ← переключение wishlist
}