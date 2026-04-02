package io.mmaltsev.vkeducation.domain.model

data class App(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val iconUrl: String? = null
)