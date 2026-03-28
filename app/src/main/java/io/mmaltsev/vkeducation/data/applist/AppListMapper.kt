package io.mmaltsev.vkeducation.data.applist

import io.mmaltsev.vkeducation.domain.model.App
import javax.inject.Inject

class AppListMapper @Inject constructor() {

    fun toDomain(dto: AppListDto): App {
        android.util.Log.d("AppListMapper", "App: ${dto.name}, iconUrl: ${dto.iconUrl}")
        return App(
            id = dto.id,
            name = dto.name,
            category = dto.category,
            description = dto.description,
            iconUrl = dto.iconUrl ?: ""
        )
    }

    fun toDomainList(dtoList: List<AppListDto>): List<App> {
        return dtoList.map { toDomain(it) }
    }
}