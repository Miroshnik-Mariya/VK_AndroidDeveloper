package io.mmaltsev.vkeducation.data.appdetails

import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import javax.inject.Inject

class AppDetailsMapper @Inject constructor() {

    fun toDomain(dto: AppDetailsDto): AppDetails {
        return AppDetails(
            id = dto.id,
            name = dto.name,
            developer = dto.developer,
            category = dto.category.toString(),
            ageRating = dto.ageRating,
            size = dto.size.toFloat(),
            iconUrl = dto.icon,
            screenshotUrlList = dto.screenshots,
            description = dto.description,
            isInWishlist = false
        )
    }
}