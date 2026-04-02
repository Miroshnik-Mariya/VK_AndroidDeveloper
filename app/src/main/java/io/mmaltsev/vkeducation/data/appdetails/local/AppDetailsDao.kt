package io.mmaltsev.vkeducation.data.appdetails.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDetailsDao {
    @Query("SELECT * FROM app_details WHERE id = :id")
    suspend fun getAppDetails(id: String): AppDetailsEntity?  // ← одноразовый запрос

    @Query("SELECT * FROM app_details WHERE id = :id")
    fun observeAppDetails(id: String): Flow<AppDetailsEntity?>  // ← Flow для наблюдения

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppDetails(entity: AppDetailsEntity)

    @Query("UPDATE app_details SET isInWishlist = :isInWishlist WHERE id = :id")
    suspend fun updateWishlistStatus(id: String, isInWishlist: Boolean)
}