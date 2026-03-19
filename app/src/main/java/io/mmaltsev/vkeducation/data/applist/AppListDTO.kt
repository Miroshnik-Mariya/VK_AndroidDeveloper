package io.mmaltsev.vkeducation.data.applist

import io.mmaltsev.vkeducation.domain.appdetails.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppListDTO(
    val id: String,
    val name: String,
    val category: Category,
    @SerialName("iconUrl")
    val icon: String,
    val description: String
)