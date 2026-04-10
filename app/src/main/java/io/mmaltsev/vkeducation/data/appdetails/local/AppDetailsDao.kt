package io.mmaltsev.vkeducation.data.appdetails.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDetailsDao {

    @Query("SELECT * FROM app_details WHERE id = :id")
    fun getAppDetails(id: String): AppDetailsEntity?  // ← убрали suspend

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAppDetails(entity: AppDetailsEntity)  // ← убрали suspend

    @Query("UPDATE app_details SET isInWishlist = :isInWishlist WHERE id = :id")
    fun updateWishlistStatus(id: String, isInWishlist: Boolean)  // ← убрали suspend
}