package io.mmaltsev.vkeducation.data.applist

import kotlinx.serialization.Serializable

@Serializable
data class AppListDto(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val iconRes: Int,
    val iconUrl: String? = null
)