package io.mmaltsev.vkeducation.data.applist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppListDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("iconUrl")  // ВАЖНО: iconUrl, а не icon
    val iconUrl: String? = null
)