package io.mmaltsev.vkeducation.data.appdetails.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import javax.inject.Inject

class AppDetailsEntityMapper @Inject constructor() {
    private val gson = Gson()

    fun toEntity(domain: AppDetails): AppDetailsEntity {
        return AppDetailsEntity(
            id = domain.id,
            name = domain.name,
            developer = domain.developer,
            category = domain.category,
            ageRating = domain.ageRating,
            size = domain.size,
            iconUrl = domain.iconUrl,
            screenshots = domain.screenshotUrlList?.let { gson.toJson(it) },
            description = domain.description,
            isInWishlist = domain.isInWishlist
        )
    }

    fun toDomain(entity: AppDetailsEntity): AppDetails {
        val screenshots = entity.screenshots?.let {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(it, type) as List<String>
        }

        return AppDetails(
            id = entity.id,
            name = entity.name,
            developer = entity.developer,
            category = entity.category.toString(),
            ageRating = entity.ageRating,
            size = entity.size,
            iconUrl = entity.iconUrl,
            screenshotUrlList = screenshots,
            description = entity.description,
            isInWishlist = entity.isInWishlist
        )
    }
}